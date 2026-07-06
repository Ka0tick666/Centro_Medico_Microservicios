package com.example.ms_paciente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients; // <-- ¡ESTE IMPORT ES EL QUE FALTA!
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients // <-- Ahora sí funcionará sin ponerse rojo
@SpringBootApplication
@EntityScan(basePackages = "com.example.ms_paciente.model") 
@EnableJpaRepositories(basePackages = "com.example.ms_paciente.repository")
public class MsPacienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPacienteApplication.class, args);
    }
}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms-paciente"<-1
//mvn clean compile spring-boot:run<-2
//http://localhost:8081/swagger-ui/index.html <--- SWAGGER<-3