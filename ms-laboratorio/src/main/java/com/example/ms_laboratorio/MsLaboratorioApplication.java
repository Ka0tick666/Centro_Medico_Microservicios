package com.example.ms_laboratorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsLaboratorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLaboratorioApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms-laboratorio"<-1
//mvn clean compile spring-boot:run<-2
//http://localhost:8086/swagger-ui/index.html<----SWAGGER<-3