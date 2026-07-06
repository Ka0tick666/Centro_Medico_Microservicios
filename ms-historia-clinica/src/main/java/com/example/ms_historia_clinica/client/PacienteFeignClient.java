package com.example.ms_historia_clinica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-paciente", url = "http://localhost:8081")
public interface PacienteFeignClient {

    @GetMapping("/api/pacientes/{id}")
    Object obtenerPacientePorId(@PathVariable("id") Integer id);
}
