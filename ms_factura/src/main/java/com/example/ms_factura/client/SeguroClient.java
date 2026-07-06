package com.example.ms_factura.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos el nombre y la URL exacta del microservicio de seguros
@FeignClient(name = "ms-seguro")
public interface SeguroClient {

    // Llama al endpoint de buscar por ID de SeguroController
    @GetMapping("/buscar/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}