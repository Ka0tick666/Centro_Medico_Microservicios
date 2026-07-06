package com.example.ms_metricacentro.config;

import com.example.ms_metricacentro.model.Metricacentro;
import com.example.ms_metricacentro.repository.MetricacentroRepository;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Locale;

@Configuration
public class DataSetupConfig {

    private final MetricacentroRepository repository;
    private final EntityManager entityManager;

    public DataSetupConfig(MetricacentroRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Bean
    @Transactional
    public CommandLineRunner poblarMetricas() {
        return args -> {
            Long totalMetricas = (Long) entityManager.createQuery("SELECT COUNT(m) FROM Metricacentro m").getSingleResult();

            if (totalMetricas == 0) {
                System.out.println("⏳ [DataFaker] Repositorio analítico vacío. Iniciando poblamiento automático en ms_metricacentro...");
                
                Faker faker = new Faker(new Locale("es"));
                
                String[] eventos = {
                    "Nueva Receta Emitida", 
                    "Bono Médico Validado con Éxito", 
                    "Alerta Enviada por Correo", 
                    "Examen de Laboratorio Registrado", 
                    "Factura Emitida"
                };

                for (int i = 0; i < 15; i++) {
                    Metricacentro metrica = new Metricacentro();
                    metrica.setNombreMetricacentro(eventos[faker.random().nextInt(eventos.length)]);
                    metrica.setValor(faker.random().nextDouble(1.0, 5.0));
                    metrica.setFecha(LocalDate.now().minusDays(faker.random().nextInt(0, 10)));
                    metrica.setIdCentro(1L);
                    
                    repository.save(metrica);
                }

                System.out.println("✅ [DataFaker] Métricas de rendimiento y KPIs del sistema inyectados correctamente.");
            } else {
                System.out.println("ℹ️ [DataFaker] La base de datos analítica ya contiene registros. Omitiendo inyección.");
            }
        };
    }
}