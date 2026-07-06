package com.example.ms_paciente.service;

import com.example.ms_paciente.model.Paciente;
import com.example.ms_paciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente guardar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Paciente buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Optional<Paciente> buscarPorRut(String rut) {
        return repository.findByRut(rut);
    }
}