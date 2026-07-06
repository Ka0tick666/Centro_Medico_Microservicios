package com.example.ms_cita.repository;

import com.example.ms_cita.model.Sobrecupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SobrecupoRepository extends JpaRepository<Sobrecupo, Integer> {
    // Para buscar si una cita específica tiene un sobrecupo asociado
    Optional<Sobrecupo> findByIdCitaOriginal(Integer idCitaOriginal);
}