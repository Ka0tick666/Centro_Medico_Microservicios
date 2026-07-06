package com.example.ms_cita.config;

import com.example.ms_cita.model.Cita;
import com.example.ms_cita.model.Sobrecupo;
import com.example.ms_cita.repository.CitaRepository;
import com.example.ms_cita.repository.SobrecupoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Configuration
public class DataSetupConfig {

    @Bean
    CommandLineRunner poblarCitasYSobrecupos(CitaRepository citaRepo, SobrecupoRepository sobrecupoRepo) {
        return args -> {
            if (citaRepo.count() == 0) {
                Faker faker = new Faker(new Locale("es-CL"));
                Random random = new Random();

                List<String> estados = Arrays.asList("PENDIENTE", "PENDIENTE", "ASISTIO", "CANCELADA");
                List<String> motivos = Arrays.asList(
                    "Control anual de rutina", "Dolor estomacal severo", 
                    "Revisión de exámenes", "Jaquequas recurrentes", "Receta médica"
                );
                List<String> doctoresAutorizadores = Arrays.asList("Dr. Aranguiz", "Dra. Poblete", "Director Médico");
                List<String> motivosUrgencia = Arrays.asList("Crisis hipertensiva", "Dolor agudo inmovilizador", "Derivación Urgente");

                System.out.println("⏳ [DataFaker] Iniciando poblamiento automático en ms-cita...");

                // Generar 10 citas base
                for (int i = 0; i < 10; i++) {
                    Cita cita = new Cita();
                    cita.setIdPaciente(faker.number().numberBetween(1, 16));
                    cita.setIdMedico(faker.number().numberBetween(1, 13));
                    cita.setFecha(LocalDate.now().plusDays(faker.number().numberBetween(1, 15)));
                    cita.setHora(LocalTime.of(faker.number().numberBetween(8, 18), 0));
                    cita.setEstado(estados.get(random.nextInt(estados.size())));
                    cita.setMotivo(motivos.get(random.nextInt(motivos.size())));

                    Cita citaGuardada = citaRepo.save(cita);

                    // Dejar las 2 primeras citas con un sobrecupo asociado
                    if (i < 2) {
                        Sobrecupo sobrecupo = new Sobrecupo();
                        sobrecupo.setIdCitaOriginal(citaGuardada.getIdCita());
                        
                        // CORREGIDO: Llama únicamente al método correcto
                        sobrecupo.setAutorizadoPor(doctoresAutorizadores.get(random.nextInt(doctoresAutorizadores.size())));
                        sobrecupo.setMotivoUrgencia(motivosUrgencia.get(random.nextInt(motivosUrgencia.size())));
                        
                        sobrecupoRepo.save(sobrecupo);
                    }
                }
                System.out.println("✅ [DataFaker] 10 Citas médicas iniciales y sobrecupos inyectados con éxito.");
            } else {
                System.out.println("ℹ️ [DataFaker] La tabla de citas ya contiene registros. Saltando inyección.");
            }
        };
    }
}