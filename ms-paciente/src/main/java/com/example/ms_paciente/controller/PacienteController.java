package com.example.ms_paciente.controller;

import com.example.ms_paciente.client.MetricacentroClient;
import com.example.ms_paciente.model.Paciente;
import com.example.ms_paciente.model.ValidacionDocumento;
import com.example.ms_paciente.repository.PacienteRepository;
import com.example.ms_paciente.repository.ValidacionRepository;
import com.example.ms_paciente.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Gestión de Pacientes", description = "Módulo para administrar los registros médicos y validaciones de documentos de identidad")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ValidacionRepository validacionRepository;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Listar todos los pacientes", description = "Retorna una lista completa de pacientes en el sistema")
    @GetMapping
    public List<Paciente> listar() {
        return service.listarTodos();
    }

    @Operation(summary = "Crear un nuevo paciente", description = "Guarda un paciente en la base de datos e informa automáticamente la métrica a la central")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente y métrica reportada"),
        @ApiResponse(responseCode = "400", description = "Error en el formato de los datos de entrada")
    })
    @PostMapping
    public Paciente crear(@RequestBody Paciente paciente) {
        Paciente pacienteGuardado = service.guardar(paciente);
        try {
            java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Nuevo Paciente Registrado: " + pacienteGuardado.getNombres());
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", java.time.LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Error métrica: " + e.getMessage());
        }
        return pacienteGuardado;
    }

    @Operation(summary = "Obtener paciente por ID", description = "Retorna la información básica de un paciente por su identificador único")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtener(@PathVariable Integer id) {
        Paciente d = service.buscarPorId(id);
        return d != null ? ResponseEntity.ok(d) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar paciente por RUT/DNI", description = "Permite localizar un paciente de forma única mediante su número de documento")
    @ApiResponse(responseCode = "404", description = "Documento no registrado")
    @GetMapping("/documento/{num}")
    public ResponseEntity<Paciente> obtenerPorDocumento(@PathVariable String num) {
        return pacienteRepository.findByNumeroDocumento(num)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Filtrar pacientes por género", description = "Retorna los pacientes que coinciden con el tipo 'M' o 'F'")
    @GetMapping("/filtrar/genero")
    public List<Paciente> listarPorGenero(@RequestParam("tipo") String tipo) {
        return pacienteRepository.findByGeneroIgnoreCase(tipo);
    }

    @Operation(summary = "Actualizar datos de un paciente", description = "Modifica la información de contacto o residencia de un paciente existente")
    @ApiResponse(responseCode = "404", description = "Paciente inválido")
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente datosNuevos) {
        if (!pacienteRepository.existsById(id)) return ResponseEntity.notFound().build();
        Paciente existente = service.buscarPorId(id);
        existente.setEmail(datosNuevos.getEmail());
        existente.setTelefono(datosNuevos.getTelefono());
        existente.setDireccion(datosNuevos.getDireccion());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @Operation(summary = "Eliminar un paciente", description = "Borra físicamente el registro de un paciente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id) {
        if (!pacienteRepository.existsById(id)) return ResponseEntity.notFound().build();
        pacienteRepository.deleteById(id);
        return ResponseEntity.ok("Paciente eliminado de la base de datos.");
    }

    @Operation(summary = "Listar todas las validaciones de documentos", description = "Retorna el historial completo de auditoría de documentos")
    @GetMapping("/validaciones/todas")
    public List<ValidacionDocumento> listarTodasLasValidaciones() {
        return validacionRepository.findAll();
    }

    @Operation(summary = "Filtrar validaciones por estado", description = "Lista documentos según estén 'Aprobado', 'Pendiente' o 'Rechazado'")
    @GetMapping("/validaciones/filtrar")
    public List<ValidacionDocumento> filtrarValidaciones(@RequestParam("estado") String estado) {
        return validacionRepository.findByEstadoValidacionIgnoreCase(estado);
    }
}