package com.example.ms_notificacion.controller;

import com.example.ms_notificacion.client.MetricacentroClient;
import com.example.ms_notificacion.dto.NotificacionRequest;
import com.example.ms_notificacion.model.Notificacion;
import com.example.ms_notificacion.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
@CrossOrigin(origins = "*")
@Tag(name = "Central de Notificaciones", description = "Dispatcher encargado de simular el envío de correos electrónicos informativos a los clientes")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @Autowired
    private MetricacentroClient metricacentroClient;

    @Operation(summary = "Ver logs de notificaciones locales")
    @GetMapping("/listar")
    public List<Notificacion> listar() {
        return service.listarTodas();
    }

    @Operation(summary = "Registrar notificación manual en bitácora")
    @PostMapping("/crear")
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return service.guardar(notificacion);
    }

    @Operation(summary = "Endpoint de consumo Feign para envío de Alertas")
    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarAlerta(@RequestBody NotificacionRequest request) {
        System.out.println("\n==================================================");
        System.out.println("[ALERTA DE NOTIFICACIÓN RECIBIDA POR OPENFEIGN]");
        System.out.println("Para: " + request.getEmailDestinatario());
        System.out.println("Mensaje: " + request.getMensaje());
        System.out.println("==================================================\n");

        try {
            java.util.Map<String, Object> reqMetrica = new java.util.HashMap<>();
            reqMetrica.put("nombreMetricacentro", "Alerta Enviada con éxito por Correo");
            reqMetrica.put("valor", 1.0);
            reqMetrica.put("fecha", java.time.LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L);
            metricacentroClient.registrarMetrica(reqMetrica);
        } catch (Exception e) {
            System.out.println("⚠️ Métrica de notificación falló.");
        }
        return ResponseEntity.ok().build();
    }
}