package com.example.ms_metricacentro.controller;

import com.example.ms_metricacentro.model.Metricacentro;
import com.example.ms_metricacentro.service.MetricacentroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/metricacentro")
@Tag(name = "Centralizador de Métricas", description = "Core operativo que unifica los logs y contadores transaccionales de todo el clúster médico")
public class MetricacentroController {

    @Autowired
    private MetricacentroService service;

    @Operation(summary = "Listar el cuadro de mando general de métricas")
    @GetMapping("/listar")
    public List<Metricacentro> listar() {
        return service.listarTodas();
    }

    @Operation(summary = "Registrar métrica transaccional", description = "Recibe los disparos asíncronos/síncronos de Feign provenientes de los demás servicios")
    @PostMapping("/crear")
    public Metricacentro crear(@RequestBody Metricacentro metricacentro) {
        return service.guardar(metricacentro);
    }
}