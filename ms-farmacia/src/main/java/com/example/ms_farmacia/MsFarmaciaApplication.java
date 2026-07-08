package com.example.ms_farmacia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsFarmaciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFarmaciaApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms-farmacia"<-1
//mvn clean compile spring-boot:run<-2
//http://localhost:8085/swagger-ui/index.html<-----SWAGGER<-3
//http://localhost:8085/api/farmacia/listar <---------apiAIVENSQL