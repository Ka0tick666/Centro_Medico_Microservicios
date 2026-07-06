package com.example.ms_notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsNotificacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsNotificacionApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms_notificacion"<-1
//mvn clean compile spring-boot:run<-2
//http://localhost:8089/swagger-ui/index.html<----SWAGGER<-3