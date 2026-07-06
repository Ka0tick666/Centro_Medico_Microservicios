package com.example.ms_historia_clinica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "spring.cloud.openfeign.circuitbreaker.enabled=false"
})
@ActiveProfiles("test")
class MsHistoriaClinicaApplicationTests {

    @Test
    void contextLoads() {
        // Test básico para verificar la carga del contexto sin levantar infraestructura real
    }
}