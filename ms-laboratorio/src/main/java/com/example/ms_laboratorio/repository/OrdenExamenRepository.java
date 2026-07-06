package com.example.ms_laboratorio.repository;

import com.example.ms_laboratorio.model.OrdenExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdenExamenRepository extends JpaRepository<OrdenExamen, Integer> {
    
    // Método 1: Buscar todas las órdenes de un paciente específico
    List<OrdenExamen> findByIdPaciente(Integer idPaciente);
    
    // Método 4: Filtrar órdenes por su estado (Pendiente, Finalizado)
    List<OrdenExamen> findByEstado(String estado);
    
    // Método 5: Buscar órdenes por tipo de examen (Ej: Hemograma)
    List<OrdenExamen> findByTipoExamenContainingIgnoreCase(String tipoExamen);
}
