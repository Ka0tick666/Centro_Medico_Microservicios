package com.example.ms_doctor.config;

import com.example.ms_doctor.model.Doctor;
import com.example.ms_doctor.model.HorarioDoctor;
import com.example.ms_doctor.repository.DoctorRepository;
import com.example.ms_doctor.repository.HorarioDoctorRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Configuration
public class DataSetupConfig {

    @Bean
    CommandLineRunner poblarDoctoresYHorarios(DoctorRepository doctorRepo, HorarioDoctorRepository horarioRepo) {
        return args -> {
            if (doctorRepo.count() == 0) {
                Faker faker = new Faker(new Locale("es-CL"));
                Random random = new Random();

                List<String> especialidades = Arrays.asList(
                    "Cardiología", "Pediatría", "Medicina General", 
                    "Dermatología", "Traumatología", "Ginecología", "Neurología"
                );

                List<String> dias = Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");

                System.out.println("⏳ [DataFaker] Iniciando poblamiento automático en ms-doctor...");

                for (int i = 0; i < 12; i++) {
                    // 1. Crear Doctor
                    Doctor doc = new Doctor();
                    doc.setNombres(faker.name().firstName());
                    doc.setApellidos(faker.name().lastName());
                    // Genera licencia médica única
                    doc.setLicenciaMedica("MED-" + faker.number().numberBetween(10000, 99999));
                    doc.setEspecialidad(especialidades.get(random.nextInt(especialidades.size())));
                    doc.setTelefono("+569" + faker.number().digits(8));
                    doc.setEmail(faker.internet().emailAddress(doc.getNombres().toLowerCase()));

                    Doctor doctorGuardado = doctorRepo.save(doc);

// 2. Asignarle 2 bloques de horarios en salas/consultorios aleatorios
                    for (int h = 0; h < 2; h++) {
                        HorarioDoctor horario = new HorarioDoctor();
                        horario.setIdDoctor(doctorGuardado.getIdDoctor());
                        horario.setDiaSemana(dias.get(random.nextInt(dias.size())));
                        
                        LocalTime inicio = LocalTime.of(faker.number().numberBetween(8, 13), 0);
                        horario.setHoraInicio(inicio);
                        horario.setHoraFin(inicio.plusHours(4));
                        horario.setConsultorio("Box " + faker.number().numberBetween(101, 308));

                        horarioRepo.save(horario);
                    }
                }
                System.out.println("✅ [DataFaker] 12 Doctores y sus agendas inyectados exitosamente.");
            } else {
                System.out.println("ℹ️ [DataFaker] La tabla de doctores ya contiene datos. Saltando inyección.");
            }
        };
    }
}
