package com.example.ms_cita.client;

import com.example.ms_cita.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-doctor", url = "http://localhost:8082/api/doctores")
public interface DoctorClient {
    @GetMapping("/{id}")
    DoctorDTO getDoctor(@PathVariable("id") Integer id);
}