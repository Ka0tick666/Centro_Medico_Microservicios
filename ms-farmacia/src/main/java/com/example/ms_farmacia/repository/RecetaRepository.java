package com.example.ms_farmacia.repository;

import com.example.ms_farmacia.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    // Método 1: Buscar recetas por paciente
    List<Receta> findByIdPaciente(Integer idPaciente);

    // Método 4: Filtrar recetas por estado (Ej: "Entregado", "Pendiente")
    List<Receta> findByEstado(String estado);

    // Método 5: Buscar recetas emitidas por un médico específico
    List<Receta> findByIdMedico(Integer idMedico);
}
