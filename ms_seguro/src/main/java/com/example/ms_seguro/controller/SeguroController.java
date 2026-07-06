package com.example.ms_seguro.controller;

import com.example.ms_seguro.client.MetricacentroClient;
import com.example.ms_seguro.model.Seguro;
import com.example.ms_seguro.service.SeguroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seguro")
@Tag(name = "Seguros y Convenios", description = "Administración de coberturas médicas corporativas y validaciones de Isapres/Fonasa")
public class SeguroController {

    @Autowired
    private SeguroService service;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Listar coberturas y convenios activos")
    @GetMapping("/listar")
    public List<Seguro> listar() {
        return service.obtenerTodos();
    }

    @Operation(summary = "Dar de alta un nuevo convenio de seguro")
    @PostMapping("/crear")
    public Seguro crear(@RequestBody Seguro seguro) {
        Seguro seguroGuardado = service.guardarSeguro(seguro);
        try {
            java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Nuevo Seguro/Convenio Procesado");
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", java.time.LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Métrica seguro falló.");
        }
        return seguroGuardado;
    }
}