package com.example.ms_seguro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsSeguroApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsSeguroApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms_seguro"<-1
//mvn clean compile spring-boot:run<-2
//http://localhost:8088/swagger-ui/index.html<-----SWAGGER<-3
//http://localhost:8088/api/seguro/listar