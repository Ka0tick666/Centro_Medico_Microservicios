package com.example.ms_laboratorio.controller;

import com.example.ms_laboratorio.client.MetricacentroClient;
import com.example.ms_laboratorio.model.OrdenExamen;
import com.example.ms_laboratorio.model.ResultadoLaboratorio;
import com.example.ms_laboratorio.service.OrdenExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laboratorio")
@CrossOrigin(origins = "*")
@Tag(name = "Laboratorio Clínico", description = "Endpoints para la gestión de órdenes de exámenes de sangre, imágenes y resultados de laboratorio")
public class OrdenExamenController {

    @Autowired
    private OrdenExamenService ordenService;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Obtener órdenes por ID de Paciente")
    @GetMapping("/paciente/{idPaciente}")
    public List<OrdenExamen> getByPaciente(@PathVariable Integer idPaciente) {
        return ordenService.obtenerPorPaciente(idPaciente);
    }

    @Operation(summary = "Listar todas las órdenes de exámenes")
    @GetMapping("/ordenes")
    public List<OrdenExamen> getAll() {
        return ordenService.listarTodas();
    }

    @Operation(summary = "Buscar orden médica por ID")
    @GetMapping("/orden/{id}")
    public ResponseEntity<OrdenExamen> getById(@PathVariable Integer id) {
        return ordenService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Generar una nueva orden de examen")
    @PostMapping("/orden")
    public OrdenExamen crearOrden(@RequestBody OrdenExamen orden) {
        // Se cambió guardarOrden() por registrarOrden() que es el que existe en tu Service
        return ordenService.registrarOrden(orden);
    }

    @Operation(summary = "Cargar resultados a una orden", description = "Adjunta el análisis clínico e informa la métrica a la central")
    @PostMapping("/resultado")
    public ResponseEntity<ResultadoLaboratorio> cargarResultado(@RequestBody ResultadoLaboratorio resultado) {
        // Validación de seguridad por si no viene asociada la orden
        if (resultado.getOrdenExamen() == null || resultado.getOrdenExamen().getIdOrden() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Se corrigió para usar registrarResultado() pasándole el ID correspondiente
        Optional<ResultadoLaboratorio> resGuardado = ordenService.registrarResultado(
                resultado.getOrdenExamen().getIdOrden(), 
                resultado
        );

        if (resGuardado.isPresent()) {
            try {
                java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
                reqMetrica.put("nombreMetricacentro", "Examen Clínico Procesado");
                reqMetrica.put("valor", 1.0);
                reqMetrica.put("fecha", java.time.LocalDate.now().toString());
                reqMetrica.put("idCentro", 1L);
                metricacentroClient.registrarMetrica(reqMetrica);
            } catch (Exception e) {
                System.out.println("⚠️ Métrica laboratorio falló.");
            }
            return ResponseEntity.ok(resGuardado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar estado de una orden (ej: Finalizado)")
    @PutMapping("/orden/{id}/estado")
    public ResponseEntity<OrdenExamen> updateEstado(@PathVariable Integer id, @RequestParam String estado) {
        return ordenService.actualizarEstado(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una orden completa")
    @DeleteMapping("/orden/{id}")
    public ResponseEntity<Void> deleteOrden(@PathVariable Integer id) {
        return ordenService.eliminarOrden(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}