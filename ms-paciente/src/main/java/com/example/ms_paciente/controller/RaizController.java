package com.example.ms_paciente.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class RaizController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> status = new HashMap<>();
        status.put("servicio", "Microservicio de Pacientes");
        status.put("estado", "OPERATIVO - UP");
        status.put("puerto", "8081");
        return status;
    }
}