package com.example.ms_historia_clinica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "ms-farmacia", url = "http://localhost:8085")
public interface FarmaciaFeignClient {

    @GetMapping("/api/farmacia/paciente/{idPaciente}")
    List<Object> obtenerRecetasPorPaciente(@PathVariable("idPaciente") Integer idPaciente);
}