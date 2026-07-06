package com.example.ms_farmacia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "spring.cloud.openfeign.circuitbreaker.enabled=false"
})
@ActiveProfiles("test")
class MsFarmaciaApplicationTests {

    @Test
    void contextLoads() {
    }
}