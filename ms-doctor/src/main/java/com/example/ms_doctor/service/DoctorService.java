package com.example.ms_doctor.service;

import com.example.ms_doctor.model.Doctor;
import com.example.ms_doctor.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;

    public List<Doctor> listarTodos() {
        return repository.findAll();
    }

    public Doctor guardar(Doctor doctor) {
        return repository.save(doctor);
    }

    public Doctor buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Optional<Doctor> buscarPorLicencia(String licencia) {
        return repository.findByLicenciaMedica(licencia);
    }
}