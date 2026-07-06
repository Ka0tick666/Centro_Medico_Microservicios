package com.example.ms_factura.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-notificacion")
public interface NotificacionClient {

    // Endpoint genérico para disparar la creación de una notificación
    @PostMapping("/crear")
    Object crearNotificacion(@RequestBody Object notificacion);
}