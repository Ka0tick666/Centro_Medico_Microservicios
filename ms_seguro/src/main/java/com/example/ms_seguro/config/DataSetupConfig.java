package com.example.ms_seguro.config;

import com.example.ms_seguro.model.Seguro;
import com.example.ms_seguro.model.Cobertura;
import com.example.ms_seguro.repository.SeguroRepository;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class DataSetupConfig {

    private final SeguroRepository seguroRepository;
    private final EntityManager entityManager;

    // Inyección por constructor clásica de Spring
    public DataSetupConfig(SeguroRepository seguroRepository, EntityManager entityManager) {
        this.seguroRepository = seguroRepository;
        this.entityManager = entityManager;
    }

    @Bean
    @Transactional
    public CommandLineRunner poblarSeguros() {
        return args -> {
            // Hacemos una consulta rápida para ver si la tabla tiene registros
            Long totalSeguros = (Long) entityManager.createQuery("SELECT COUNT(s) FROM Seguro s").getSingleResult();

            if (totalSeguros == 0) {
                System.out.println("⏳ [DataFaker] Base de datos vacía. Iniciando poblamiento automático en ms_seguro...");
                
                Faker faker = new Faker(new Locale("es"));
                
                // Opciones típicas para convenios en Chile
                String[] instituciones = {"FONASA A", "FONASA B", "FONASA C", "FONASA D", "ISAPRE COLMENA", "ISAPRE BANMEDICA", "ISAPRE CONSALUD"};
                String[] planes = {"Básico", "Estándar", "Complementario", "Premium", "Preferente"};

                for (String institucion : instituciones) {
                    Seguro seguro = new Seguro();
                    seguro.setNombreInstitucion(institucion);
                    seguro.setTipoPlan(planes[faker.random().nextInt(planes.length)]);
                    
                    List<Cobertura> coberturas = new ArrayList<>();
                    
                    // Creamos 2 coberturas simuladas con pacientes y porcentajes aleatorios por cada seguro
                    for (int j = 0; j < 2; j++) {
                        Cobertura cobertura = new Cobertura();
                        cobertura.setIdPaciente((long) faker.random().nextInt(1, 50));
                        cobertura.setPorcentaje(faker.random().nextDouble(10.0, 100.0));
                        cobertura.setSeguro(seguro); // Vinculamos la relación bidireccional foránea
                        coberturas.add(cobertura);
                    }
                    
                    seguro.setCoberturas(coberturas);
                    // Guarda el seguro y sus coberturas gracias al CascadeType.ALL que tienes en la entidad
                    seguroRepository.save(seguro);
                }

                System.out.println("✅ [DataFaker] Convenios y coberturas iniciales insertados con éxito.");
            } else {
                System.out.println("ℹ️ [DataFaker] La base de datos ya contiene información. Omitiendo inyección.");
            }
        };
    }
}