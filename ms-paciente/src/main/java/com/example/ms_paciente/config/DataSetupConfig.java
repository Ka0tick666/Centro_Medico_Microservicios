package com.example.ms_paciente.config;

import com.example.ms_paciente.model.Paciente;
import com.example.ms_paciente.repository.PacienteRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.ZoneId;
import java.util.Locale;

@Configuration
public class DataSetupConfig {

    @Bean
    CommandLineRunner initDatabase(PacienteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Configuramos Faker en español
                Faker faker = new Faker(new Locale("es"));
                
                String[] generos = {"Masculino", "Femenino", "Otro"};

                for (int i = 0; i < 15; i++) {
                    Paciente p = new Paciente();
                    p.setTipoDocumento("RUN");
                    // Genera un RUT ficticio estructurado
                    p.setNumeroDocumento(faker.number().numberBetween(10000000, 25000000) + "-" + faker.number().numberBetween(0, 9));
                    p.setRut(p.getNumeroDocumento());
                    p.setNombres(faker.name().firstName());
                    p.setApellidos(faker.name().lastName());
                    p.setFechaNacimiento(faker.date().birthday(18, 85).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    p.setGenero(generos[faker.number().numberBetween(0, 3)]);
                    p.setEmail(faker.internet().emailAddress(p.getNombres().toLowerCase() + i));
                    p.setTelefono("+569" + faker.number().digits(8));
                    p.setDireccion(faker.address().streetAddress() + ", " + faker.address().city());
                    
                    repository.save(p);
                }
                System.out.println("✅ [DataFaker] 15 Pacientes aleatorios inyectados con éxito en la Base de Datos.");
            }
        };
    }
}