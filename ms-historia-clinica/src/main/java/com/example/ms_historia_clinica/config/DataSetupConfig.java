package com.example.ms_historia_clinica.config;

import com.example.ms_historia_clinica.model.HistoriaClinica;
import com.example.ms_historia_clinica.model.EntradaHistoria;
import com.example.ms_historia_clinica.model.DiagnosticoCIE10;
import com.example.ms_historia_clinica.repository.HistoriaRepository;
import com.example.ms_historia_clinica.repository.EntradaHistoriaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class DataSetupConfig {

    @Bean
    CommandLineRunner poblarFichasClinicas(HistoriaRepository historiaRepo, EntradaHistoriaRepository entradaRepo) {
        return args -> {
            if (historiaRepo.count() == 0) {
                Faker faker = new Faker(new Locale("es-CL"));

                System.out.println("⏳ [DataFaker] Iniciando poblamiento automático en ms-historia-clinica...");

                // 1. Crear historia clínica base para el paciente 1
                HistoriaClinica historia = new HistoriaClinica();
                historia.setIdPaciente(1);
                historia.setFechaCreacion(LocalDateTime.now().minusMonths(6));
                historia.setGrupoSanguineo("O+");
                historia.setAntecedentes("Hipertensión arterial crónica. Sin alergias conocidas.");
                
                HistoriaClinica historiaGuardada = historiaRepo.save(historia);

                // 2. Crear una evolución médica (Entrada)
                EntradaHistoria entrada = new EntradaHistoria();
                entrada.setIdCita(1);
                entrada.setDetalleConsulta("Paciente asiste a control. Refiere cefalea ocasional. Se ajusta dosis de medicamento.");
                entrada.setHistoriaClinica(historiaGuardada);

                // 3. Asignar Diagnósticos CIE10 a la entrada
                DiagnosticoCIE10 cie10 = new DiagnosticoCIE10();
                cie10.setCodigoCie10("I10");
                cie10.setDescripcion("Hipertensión esencial (primaria)");
                cie10.setEntradaHistoria(entrada);

                List<DiagnosticoCIE10> diagnosticos = new ArrayList<>();
                diagnosticos.add(cie10);
                entrada.setDiagnosticos(diagnosticos);

                // Guardar la entrada (cascadea los diagnósticos automáticamente)
                entradaRepo.save(entrada);

                System.out.println("✅ [DataFaker] Ficha clínica inicial cargada con éxito para Paciente ID 1.");
            } else {
                System.out.println("ℹ️ [DataFaker] La tabla historia_clinica ya contiene datos. Saltando inyección.");
            }
        };
    }
}