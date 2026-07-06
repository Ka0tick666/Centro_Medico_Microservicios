package com.example.ms_paciente.repository;

import com.example.ms_paciente.model.ValidacionDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ValidacionRepository extends JpaRepository<ValidacionDocumento, Integer> {
    
    // Mapeo automático perfecto gracias a la unificación del modelo en CamelCase
    List<ValidacionDocumento> findByEstadoValidacionIgnoreCase(String estadoValidacion);
}