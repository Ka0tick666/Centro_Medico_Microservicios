package com.example.ms_historia_clinica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "ms-laboratorio", url = "http://localhost:8086")
public interface LaboratorioFeignClient {

    @GetMapping("/api/laboratorio/paciente/{idPaciente}")
    List<Object> obtenerExamenesPorPaciente(@PathVariable("idPaciente") Integer idPaciente);
}
