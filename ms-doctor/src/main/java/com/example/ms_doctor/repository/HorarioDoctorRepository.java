package com.example.ms_doctor.repository;

import com.example.ms_doctor.model.HorarioDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HorarioDoctorRepository extends JpaRepository<HorarioDoctor, Integer> {

    // Al limpiar la entidad a CamelCase, este método responde nativamente sin errores
    List<HorarioDoctor> findByIdDoctor(Integer idDoctor);

    // Método adicional para filtrar horarios específicos por consultorio
    List<HorarioDoctor> findByConsultorioIgnoreCase(String consultorio);
}