package com.example.ms_metricacentro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsMetricacentroApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsMetricacentroApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms_metricacentro"<-1
//mvn clean compile spring-boot:run<-2
//http://localhost:8090/swagger-ui/index.html<---SWAGGER<-3
//http://localhost:8090/api/metricacentro/listar