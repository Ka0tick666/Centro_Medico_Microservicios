package com.example.ms_farmacia.controller;

import com.example.ms_farmacia.client.MetricacentroClient;
import com.example.ms_farmacia.client.PacienteClient;
import com.example.ms_farmacia.client.DoctorClient;
import com.example.ms_farmacia.client.NotificacionClient;
import com.example.ms_farmacia.dto.PacienteDTO;
import com.example.ms_farmacia.dto.DoctorDTO;
import com.example.ms_farmacia.dto.NotificacionRequest;
import com.example.ms_farmacia.model.Receta;
import com.example.ms_farmacia.service.RecetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/farmacia")
@CrossOrigin(origins = "*")
@Tag(name = "Farmacia y Recetas", description = "Emisión de recetas médicas con flujos automáticos de email mediante ms-notificacion")
public class RecetaController {

    private final RecetaService recetaService;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private NotificacionClient notificacionClient;

    @Autowired
    private MetricacentroClient metricacentroClient;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @Operation(summary = "Ver el historial clínico de recetas emitidas en el hospital (Soporte HATEOAS)")
    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Receta>>> listar() {
        // Mapeamos cada receta a un EntityModel agregándole sus propios hiperenlaces dinámicos
        List<EntityModel<Receta>> recetas = recetaService.listarTodas().stream()
                .map(receta -> EntityModel.of(receta,
                        linkTo(methodOn(RecetaController.class).obtenerPorId(receta.getIdReceta())).withSelfRel(),
                        linkTo(methodOn(RecetaController.class).obtenerDetalleCompleto(receta.getIdReceta())).withRel("detalle-integrado"),
                        linkTo(methodOn(RecetaController.class).listar()).withRel("lista-completa")))
                .collect(Collectors.toList());

        // Envolvemos la lista en un CollectionModel con el enlace al recurso raíz
        CollectionModel<EntityModel<Receta>> model = CollectionModel.of(recetas,
                linkTo(methodOn(RecetaController.class).listar()).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Buscar receta médica por ID (Soporte HATEOAS)")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Receta>> obtenerPorId(@PathVariable Integer id) {
        return recetaService.buscarPorId(id)
                .map(receta -> {
                    EntityModel<Receta> model = EntityModel.of(receta,
                            linkTo(methodOn(RecetaController.class).obtenerPorId(id)).withSelfRel(),
                            linkTo(methodOn(RecetaController.class).obtenerDetalleCompleto(id)).withRel("detalle-integrado"),
                            linkTo(methodOn(RecetaController.class).listar()).withRel("lista-completa"));
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Emitir nueva receta de medicamentos (Genera flujos HATEOAS, Alertas y Métricas)")
    @PostMapping("/crear")
    public ResponseEntity<EntityModel<Receta>> crear(@RequestBody Receta receta) {
        Receta guardada = recetaService.registrarReceta(receta);

        // Orquestación asíncrona/síncrona original hacia otros servicios
        try {
            Map<String, Object> reqMetrica = new HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Nueva Receta Emitida");
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", java.time.LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Métrica analítica no pudo registrarse.");
        }

        try {
            PacienteDTO p = pacienteClient.getPaciente(guardada.getIdPaciente());
            if (p != null && p.getEmail() != null) {
                NotificacionRequest notif = new NotificacionRequest(
                        p.getEmail(),
                        "Hola " + p.getNombres() + ", se ha generado una nueva receta médica para ti."
                );
                notificacionClient.enviarAlerta(notif);
                System.out.println("✅ Alerta enviada con éxito al ms-notificacion.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ ms-paciente o ms-notificacion caídos, correo omitido.");
        }

        // Construcción de la respuesta HATEOAS para el elemento recién creado
        EntityModel<Receta> model = EntityModel.of(guardada,
                linkTo(methodOn(RecetaController.class).obtenerPorId(guardada.getIdReceta())).withSelfRel(),
                linkTo(methodOn(RecetaController.class).obtenerDetalleCompleto(guardada.getIdReceta())).withRel("detalle-integrado"),
                linkTo(methodOn(RecetaController.class).listar()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity
                .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @Operation(summary = "Consolidar reporte integrado de receta (Feign)")
    @GetMapping("/{id}/detalle-completo")
    public ResponseEntity<String> obtenerDetalleCompleto(@PathVariable Integer id) {
        return recetaService.buscarPorId(id).map(receta -> {
            PacienteDTO p = null; DoctorDTO d = null;
            try { p = pacienteClient.getPaciente(receta.getIdPaciente()); } catch (Exception e) {}
            try { d = doctorClient.getDoctor(receta.getIdMedico()); } catch (Exception e) {}

            StringBuilder sb = new StringBuilder();
            sb.append("=== DETALLE INTEGRADO DE RECETA MÉDICA ===\n");
            sb.append("ID Receta: ").append(receta.getIdReceta()).append("\n");
            sb.append("Estado Actual: ").append(receta.getEstado()).append("\n");
            
            if (p != null) sb.append("Paciente Asignado: ").append(p.getNombres()).append(" (RUT: ").append(p.getRut()).append(")\n");
            if (d != null) sb.append("Médico Prescriptor: ").append(d.getNombres()).append(" [").append(d.getEspecialidad()).append("]\n");
            sb.append("------------------------------------------");
            return ResponseEntity.ok(sb.toString());
        }).orElse(ResponseEntity.notFound().build());
    }
}