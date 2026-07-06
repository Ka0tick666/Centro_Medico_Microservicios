package com.example.ms_farmacia.client;

import com.example.ms_farmacia.dto.NotificacionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-notificacion-client", url = "http://localhost:8089/api/notificacion")
public interface NotificacionClient {

    @PostMapping("/enviar")
    void enviarAlerta(@RequestBody NotificacionRequest request);
}