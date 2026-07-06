package com.example.ms_doctor.repository;

import com.example.ms_doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    // Método para filtrar doctores por especialidad (Ignorando mayúsculas/minúsculas)
    List<Doctor> findByEspecialidadIgnoreCase(String especialidad);

    // Método para buscar un doctor por su licencia médica única
    Optional<Doctor> findByLicenciaMedica(String licenciaMedica);
}