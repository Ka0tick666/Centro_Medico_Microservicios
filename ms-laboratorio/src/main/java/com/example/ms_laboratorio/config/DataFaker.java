package com.example.ms_laboratorio.config;

import com.example.ms_laboratorio.model.OrdenExamen;
import com.example.ms_laboratorio.model.ResultadoLaboratorio;
import com.example.ms_laboratorio.repository.OrdenExamenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DataFaker implements CommandLineRunner {

    private final OrdenExamenRepository ordenRepository;

    public DataFaker(OrdenExamenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (ordenRepository.count() == 0) {
            System.out.println("⏳ [DataFaker] Iniciando poblamiento automático en ms-laboratorio...");

            OrdenExamen orden = new OrdenExamen();
            orden.setIdPaciente(1); 
            orden.setIdMedico(2);
            orden.setTipoExamen("Hemograma Completo y Perfil Bioquímico");
            orden.setFechaSolicitud(LocalDate.now().minusDays(2));
            orden.setEstado("Finalizado");
            orden.setResultas(new ArrayList<>()); 

            ResultadoLaboratorio resultado1 = new ResultadoLaboratorio();
            resultado1.setValor("14.5 g/dL");
            resultado1.setRangoReferencia("13.8 - 17.2 g/dL");
            resultado1.setObservaciones("Niveles de hemoglobina estables dentro del rango normal.");
            resultado1.setFechaResultado(LocalDate.now());
            resultado1.setOrdenExamen(orden); 

            ResultadoLaboratorio resultado2 = new ResultadoLaboratorio();
            resultado2.setValor("95 mg/dL");
            resultado2.setRangoReferencia("70 - 100 mg/dL");
            resultado2.setObservaciones("Glucemia en ayunas controlada.");
            resultado2.setFechaResultado(LocalDate.now());
            resultado2.setOrdenExamen(orden);

            orden.getResultas().add(resultado1);
            orden.getResultas().add(resultado2);

            ordenRepository.save(orden);
            System.out.println("✅ [DataFaker] Órdenes y resultados analíticos inyectados con éxito.");
        } else {
            System.out.println("ℹ️ [DataFaker] La base de datos de laboratorio ya contiene datos. Omitiendo.");
        }
    }
}
