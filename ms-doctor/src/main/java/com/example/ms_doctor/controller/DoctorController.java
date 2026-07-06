package com.example.ms_doctor.controller;

import com.example.ms_doctor.client.MetricacentroClient;
import com.example.ms_doctor.model.Doctor;
import com.example.ms_doctor.model.HorarioDoctor;
import com.example.ms_doctor.repository.DoctorRepository;
import com.example.ms_doctor.repository.HorarioDoctorRepository;
import com.example.ms_doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
@Tag(name = "Gestión de Médicos", description = "Módulo para administrar el personal médico, licencias y sus agendas de horarios")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private HorarioDoctorRepository horarioRepo;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Listar todos los doctores", description = "Obtiene el personal médico global del centro de salud")
    @GetMapping
    public List<Doctor> listar() {
        return service.listarTodos();
    }

    @Operation(summary = "Registrar un nuevo doctor", description = "Crea el médico e informa la métrica de un nuevo profesional activo")
    @PostMapping
    public Doctor crear(@RequestBody Doctor doctor) {
        Doctor doctorGuardado = service.guardar(doctor);
        try {
            java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Nuevo Doctor Staff: " + doctorGuardado.getNombres());
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", java.time.LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Métrica falló: " + e.getMessage());
        }
        return doctorGuardado;
    }

    @Operation(summary = "Obtener doctor por ID", description = "Busca la ficha de un médico específico")
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> obtener(@PathVariable Integer id) {
        Doctor d = service.buscarPorId(id);
        return d != null ? ResponseEntity.ok(d) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar por Licencia Médica", description = "Busca un doctor mediante su registro nacional único de licencia")
    @GetMapping("/licencia/{licencia}")
    public ResponseEntity<Doctor> buscarPorLicencia(@PathVariable String licencia) {
        return doctorRepo.findByLicenciaMedica(licencia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Filtrar médicos por especialidad", description = "Obtiene la lista de doctores según su área (ej: Infectología, Cardiología)")
    @GetMapping("/filtrar/especialidad")
    public List<Doctor> filtrarPorEspecialidad(@RequestParam("nombre") String nombre) {
        return doctorRepo.findByEspecialidadIgnoreCase(nombre);
    }

    @Operation(summary = "Actualizar información de un doctor")
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> actualizarDoctor(@PathVariable Integer id, @RequestBody Doctor datosNuevos) {
        if (!doctorRepo.existsById(id)) return ResponseEntity.notFound().build();
        Doctor existente = service.buscarPorId(id);
        existente.setEspecialidad(datosNuevos.getEspecialidad());
        existente.setTelefono(datosNuevos.getTelefono());
        existente.setEmail(datosNuevos.getEmail());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @Operation(summary = "Eliminar un doctor")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDoctor(@PathVariable Integer id) {
        if (!doctorRepo.existsById(id)) return ResponseEntity.notFound().build();
        doctorRepo.deleteById(id);
        return ResponseEntity.ok("Doctor eliminado correctamente de la base de datos.");
    }

    @Operation(summary = "Asignar bloque de horario", description = "Registra un día, rango de horas y consultorio para un médico")
    @PostMapping("/horarios")
    public HorarioDoctor asignarHorario(@RequestBody HorarioDoctor horario) {
        return horarioRepo.save(horario);
    }

    @Operation(summary = "Ver horarios de un doctor", description = "Obtiene los bloques de consulta asignados a un médico por su ID")
    @GetMapping("/{id}/horarios")
    public List<HorarioDoctor> verHorarios(@PathVariable Integer id) {
        return horarioRepo.findByIdDoctor(id);
    }

    @Operation(summary = "Listar todos los horarios del sistema")
    @GetMapping("/horarios/todos")
    public List<HorarioDoctor> listarTodosLosHorarios() {
        return horarioRepo.findAll();
    }

    @Operation(summary = "Filtrar horarios por consultorio/sala")
    @GetMapping("/horarios/consultorio")
    public List<HorarioDoctor> filtrarPorConsultorio(@RequestParam("sala") String sala) {
        return horarioRepo.findByConsultorioIgnoreCase(sala);
    }
}