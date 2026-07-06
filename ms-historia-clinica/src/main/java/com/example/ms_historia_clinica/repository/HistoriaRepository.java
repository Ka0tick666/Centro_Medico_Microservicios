package com.example.ms_historia_clinica.repository;

import com.example.ms_historia_clinica.model.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface HistoriaRepository extends JpaRepository<HistoriaClinica, Integer> {
    Optional<HistoriaClinica> findByIdPaciente(Integer idPaciente);
    List<HistoriaClinica> findByGrupoSanguineo(String grupoSanguineo);
}
