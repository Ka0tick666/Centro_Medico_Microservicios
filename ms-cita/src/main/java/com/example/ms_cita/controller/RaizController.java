package com.example.ms_cita.controller; // <-- Ojo, cambia esto si el paquete de tus controladores es diferente

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class RaizController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> status = new HashMap<>();
        status.put("servicio", "Microservicio de Citas Médicas");
        status.put("estado", "OPERATIVO - UP");
        status.put("puerto", "8081"); // <-- Asegúrate de que el puerto sea el que definiste en su yml (creo que era el 8081)
        return status;
    }
}