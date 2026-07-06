package com.example.ms_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGatewayApplication.class, args);
	}

}
//cd "C:\Users\Plaza Vespucio\Desktop\Centro_Medico\ms-gateway"<-1
//mvn clean compile spring-boot:run -Dmaven.test.skip=true<-2