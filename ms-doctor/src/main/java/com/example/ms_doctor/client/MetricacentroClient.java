package com.example.ms_doctor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-metricacentro", url = "http://localhost:8090/api/metricacentro")
public interface MetricacentroClient {
    @PostMapping("/crear")
    Object registrarMetrica(@RequestBody Object metrica);
}