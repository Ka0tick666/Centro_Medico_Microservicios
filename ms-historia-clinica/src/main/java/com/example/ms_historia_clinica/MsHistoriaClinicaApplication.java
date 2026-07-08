package com.example.ms_historia_clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsHistoriaClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsHistoriaClinicaApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms-historia-clinica"<-1
//mvn clean compile spring-boot:run<--- prender el microservicio<-2
//http://localhost:8084/swagger-ui/index.html<------SWAGGER<-3
//http://localhost:8084/api/historia/paciente/1/completo<--- api AIVENSQL