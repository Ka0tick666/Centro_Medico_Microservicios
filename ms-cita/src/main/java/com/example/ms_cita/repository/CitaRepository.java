package com.example.ms_cita.repository;

import com.example.ms_cita.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    // Buscar citas de un paciente
    List<Cita> findByIdPaciente(Integer idPaciente);
    
    // Buscar citas de un médico
    List<Cita> findByIdMedico(Integer idMedico);

    // Buscar citas por una fecha específica
    List<Cita> findByFecha(LocalDate fecha);

    // Buscar citas por estado (PENDIENTE, CANCELADA, ASISTIO)
    List<Cita> findByEstadoIgnoreCase(String estado);
}