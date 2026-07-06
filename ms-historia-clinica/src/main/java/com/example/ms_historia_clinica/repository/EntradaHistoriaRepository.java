package com.example.ms_historia_clinica.repository;

import com.example.ms_historia_clinica.model.EntradaHistoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntradaHistoriaRepository extends JpaRepository<EntradaHistoria, Integer> {
    // Al extender JpaRepository ya heredas automáticamente .save(), .deleteById() y .existsById()
}
