package com.example.ms_farmacia.client;

import com.example.ms_farmacia.dto.PacienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-paciente", url = "http://localhost:8081/api/pacientes")
public interface PacienteClient {
    @GetMapping("/{id}")
    PacienteDTO getPaciente(@PathVariable("id") Integer id);
}