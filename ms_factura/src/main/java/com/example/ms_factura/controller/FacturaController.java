package com.example.ms_factura.controller;

import com.example.ms_factura.client.MetricacentroClient;
import com.example.ms_factura.model.Factura;
import com.example.ms_factura.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/factura")
@Tag(name = "Finanzas e Ingresos", description = "Endpoints para el control comercial y emisión de facturaciones clínicas")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Listar todas las facturas emitidas")
    @GetMapping("/listar")
    public List<Factura> listar() {
        return service.listarTodas();
    }

    @Operation(summary = "Generar un cobro / factura", description = "Inserta la transacción contable y actualiza el contador de transacciones en la central")
    @PostMapping("/crear")
    public Factura crear(@RequestBody Factura factura) {
        Factura facturaGuardada = service.guardar(factura);
        try {
            java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Nueva Factura Emitida - ID: " + facturaGuardada.getIdFactura());
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", java.time.LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Métrica facturación errónea.");
        }
        return facturaGuardada;
    }
}