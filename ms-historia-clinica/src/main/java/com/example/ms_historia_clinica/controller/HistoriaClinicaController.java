package com.example.ms_historia_clinica.controller;

import com.example.ms_historia_clinica.client.MetricacentroClient;
import com.example.ms_historia_clinica.model.HistoriaClinica;
import com.example.ms_historia_clinica.model.EntradaHistoria;
import com.example.ms_historia_clinica.model.HistoriaCompletaDTO;
import com.example.ms_historia_clinica.service.HistoriaClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historia")
@CrossOrigin(origins = "*")
@Tag(name = "Historia Clínica", description = "Controlador maestro de la ficha médica e integraciones complejas del paciente")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService historiaService;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Obtener Historia Clínica Completa", description = "Trae la ficha base, más exámenes de laboratorio y recetas unificadas")
    @GetMapping("/paciente/{idPaciente}/completo")
    public ResponseEntity<HistoriaCompletaDTO> getHistoriaCompleta(@PathVariable Integer idPaciente) {
        return historiaService.getHistoriaCompletaPorPaciente(idPaciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear o registrar carpeta de historia clínica")
    @PostMapping("/crear")
    public ResponseEntity<HistoriaClinica> crearHistoria(@RequestBody HistoriaClinica historia) {
        HistoriaClinica nuevaHistoria = historiaService.guardarHistoria(historia);
        return ResponseEntity.ok(nuevaHistoria);
    }

    @Operation(summary = "Agregar una nueva evolución/entrada médica a la ficha por ID de Historia")
    @PostMapping("/{idHistoria}/entrada")
    public ResponseEntity<EntradaHistoria> crearEntrada(@PathVariable Integer idHistoria, @RequestBody EntradaHistoria entrada) {
        
        // CORREGIDO: Se pasa directamente el idHistoria de la URL al servicio, evitando fijar el ID 1
        java.util.Optional<EntradaHistoria> entradaGuardada = historiaService.agregarEntradaMedica(idHistoria, entrada);

        if (entradaGuardada.isPresent()) {
            try {
                java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
                reqMetrica.put("nombreMetricacentro", "Nueva Ficha Clínica Actualizada");
                reqMetrica.put("valor", 1.0);
                reqMetrica.put("fecha", java.time.LocalDate.now().toString());
                reqMetrica.put("idCentro", 1L);
                metricacentroClient.registrarMetrica(reqMetrica);
            } catch (Exception e) {
                System.out.println("⚠️ Métrica historia clínica falló.");
            }
            return ResponseEntity.ok(entradaGuardada.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar antecedentes médicos de la ficha")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HistoriaClinica> update(@PathVariable Integer id, @RequestBody HistoriaClinica datos) {
        return historiaService.actualizarDatos(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar historia completa de la base de datos")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteHistoria(@PathVariable Integer id) {
        return historiaService.eliminarHistoriaCompleta(id) 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.notFound().build();
    }
}