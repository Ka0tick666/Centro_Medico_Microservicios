package com.example.ms_historia_clinica.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class RaizController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> status = new HashMap<>();
        status.put("servicio", "Microservicio de Historia Clínica");
        status.put("estado", "OPERATIVO - UP");
        status.put("puerto", "8084");
        return status;
    }
}