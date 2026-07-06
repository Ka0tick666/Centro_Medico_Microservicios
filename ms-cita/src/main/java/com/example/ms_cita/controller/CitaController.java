package com.example.ms_cita.controller;

import com.example.ms_cita.client.MetricacentroClient;
import com.example.ms_cita.model.Cita;
import com.example.ms_cita.model.Sobrecupo;
import com.example.ms_cita.repository.CitaRepository;
import com.example.ms_cita.repository.SobrecupoRepository;
import com.example.ms_cita.client.PacienteClient;
import com.example.ms_cita.client.DoctorClient;
import com.example.ms_cita.dto.PacienteDTO;
import com.example.ms_cita.dto.DoctorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Agendamiento de Citas", description = "Controlador central para gestionar citas médicas, sobrecupos de urgencia e interconexión Feign")
public class CitaController {

    @Autowired
    private CitaRepository repository;

    @Autowired
    private SobrecupoRepository sobrecupoRepo; 

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Crear una nueva cita", description = "Registra una cita médica base en estado PENDIENTE")
    @PostMapping
    public Cita crearCita(@RequestBody Cita cita) {
        if (cita.getEstado() == null) cita.setEstado("PENDIENTE");
        Cita citaGuardada = repository.save(cita);
        try {
            java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Nueva Consulta Agendada");
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Métrica cita falló: " + e.getMessage());
        }
        return citaGuardada;
    }

    @Operation(summary = "Listar todas las citas")
    @GetMapping
    public List<Cita> listarTodas() {
        return repository.findAll();
    }

    @Operation(summary = "Obtener cita por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear sobrecupo de urgencia", description = "Asocia una alerta de sobrecupo y autorización a una cita existente")
    @PostMapping("/sobrecupo")
    public Sobrecupo crearSobrecupo(@RequestBody Sobrecupo sobrecupo) {
        return sobrecupoRepo.save(sobrecupo);
    }

    @Operation(summary = "Listar todos los sobrecupos")
    @GetMapping("/sobrecupos/todos")
    public List<Sobrecupo> listarSobrecupos() {
        return sobrecupoRepo.findAll();
    }

    @Operation(summary = "Filtrar citas por fecha exacta")
    @GetMapping("/buscar/fecha")
    public List<Cita> buscarPorFecha(@RequestParam("dia") String dia) {
        LocalDate fecha = LocalDate.parse(dia);
        return repository.findByFecha(fecha);
    }

    @Operation(summary = "Consolidar información inter-servicios (Feign)", description = "Consume ms-paciente y ms-doctor para devolver un reporte unificado de la consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte de texto plano generado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error de conexión Feign con microservicios externos")
    })
    @GetMapping("/{id}/detalle-completo")
    public String obtenerDetalleCompleto(@PathVariable Integer id) {
        Optional<Cita> opCita = repository.findById(id);
        if (!opCita.isPresent()) return "La cita solicitada no existe en el sistema.";
        
        Cita cita = opCita.get();
        Optional<Sobrecupo> infoSobrecupo = sobrecupoRepo.findByIdCitaOriginal(cita.getIdCita());

        try {
            PacienteDTO p = pacienteClient.getPaciente(cita.getIdPaciente());
            DoctorDTO d = doctorClient.getDoctor(cita.getIdMedico());

            StringBuilder sb = new StringBuilder();
            sb.append("--- DETALLE DE LA CITA #").append(id).append(" ---\n");
            sb.append("Fecha: ").append(cita.getFecha()).append(" | Hora: ").append(cita.getHora()).append("\n");
            sb.append("Paciente: ").append(p.getNombres()).append(" ").append(p.getApellidos()).append(" (RUT: ").append(p.getRut()).append(")\n");
            sb.append("Doctor: ").append(d.getNombres()).append(" ").append(d.getApellidos()).append(" [Especialidad: ").append(d.getEspecialidad()).append("]\n");
            sb.append("Estado: ").append(cita.getEstado()).append("\n");
            
            if (cita.getMotivo() != null) sb.append("Motivo Consulta: ").append(cita.getMotivo()).append("\n");
            if (infoSobrecupo.isPresent()) {
                sb.append("\n[!] ALERTA: ESTA CITA ES UN SOBRECUPO\n");
                sb.append("Autorizado por: ").append(infoSobrecupo.get().getAutorizadoPor()).append("\n");
                sb.append("Motivo Urgencia: ").append(infoSobrecupo.get().getMotivoUrgencia()).append("\n");
            }
            sb.append("-------------------------------");
            return sb.toString();
        } catch (Exception e) {
            return "Error Crítico: No se pudo consolidar la información inter-servicios. Verifique si ms-paciente y ms-doctor están en línea.";
        }
    }
}