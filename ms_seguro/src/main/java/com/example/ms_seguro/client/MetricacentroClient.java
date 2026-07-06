package com.example.ms_seguro.client; 

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-metricacentro")
public interface MetricacentroClient {
    @PostMapping("/crear")
    Object registrarMetrica(@RequestBody Object metrica);
}