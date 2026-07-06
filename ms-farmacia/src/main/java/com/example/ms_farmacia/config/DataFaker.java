package com.example.ms_farmacia.config;

import com.example.ms_farmacia.model.Medicamento;
import com.example.ms_farmacia.model.Receta;
import com.example.ms_farmacia.model.DetalleReceta;
import com.example.ms_farmacia.repository.RecetaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DataFaker implements CommandLineRunner {

    private final RecetaRepository recetaRepo;
    private final EntityManager em;

    public DataFaker(RecetaRepository recetaRepo, EntityManager em) {
        this.recetaRepo = recetaRepo;
        this.em = em;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Long totalMedicamentos = (Long) em.createQuery("SELECT COUNT(m) FROM Medicamento m").getSingleResult();
        
        if (totalMedicamentos == 0) {
            System.out.println("⏳ [DataFaker] Iniciando poblamiento automático en ms-farmacia...");

            // 1. Crear medicamentos base
            Medicamento med1 = new Medicamento();
            med1.setNombre("Paracetamol 500mg");
            med1.setDescripcion("Analgésico y antipirético clásico");
            med1.setStock(150);
            med1.setPrecioUnitario(1200.0);
            em.persist(med1);

            Medicamento med2 = new Medicamento();
            med2.setNombre("Losartán 500mg");
            med2.setDescripcion("Antihipertensivo regular");
            med2.setStock(90);
            med2.setPrecioUnitario(4500.0);
            em.persist(med2);

            em.flush(); // Asegurar IDs generados

            // 2. Crear receta inicial para el paciente 1
            Receta receta = new Receta();
            receta.setIdPaciente(1);
            receta.setIdMedico(1);
            receta.setFechaEmision(LocalDate.now());
            receta.setEstado("Pendiente");

            // 3. Crear el detalle asociado
            DetalleReceta detalle = new DetalleReceta();
            detalle.setIdMedicamento(med1.getIdMedicamento());
            detalle.setDosis("1 tableta");
            detalle.setFrecuencia("Cada 8 horas por 3 días");
            detalle.setReceta(receta);

            ArrayList<DetalleReceta> listaDetalles = new ArrayList<>();
            listaDetalles.add(detalle);
            receta.setDetalles(listaDetalles);

            recetaRepo.save(receta);

            System.out.println("✅ [DataFaker] Catálogo de medicamentos y receta de prueba inyectados con éxito.");
        } else {
            System.out.println("ℹ️ [DataFaker] La tabla ya cuenta con registros. Omitiendo.");
        }
    }
}