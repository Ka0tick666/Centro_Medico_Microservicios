package com.example.ms_paciente.repository;

import com.example.ms_paciente.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findByRut(String rut);

    List<Paciente> findByGeneroIgnoreCase(String genero);
    
    // Mapeo automático perfecto gracias a la unificación del modelo en CamelCase
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
}