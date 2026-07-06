package com.example.ms_farmacia.service;

import com.example.ms_farmacia.model.Receta;
import com.example.ms_farmacia.repository.RecetaRepository;
import com.example.ms_farmacia.repository.DetalleRecetaRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private DetalleRecetaRepository detalleRepository;

    @InjectMocks
    private RecetaService recetaService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testListarTodas() {
        // Generar recetas simuladas usando DataFaker dinámico
        List<Receta> recetasFicticias = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Receta r = new Receta();
            r.setIdReceta(faker.number().randomDigit());
            r.setIdPaciente(faker.number().randomDigit());
            r.setIdMedico(faker.number().randomDigit());
            r.setFechaEmision(LocalDate.now());
            r.setEstado(faker.options().option("Pendiente", "Entregado"));
            recetasFicticias.add(r);
        }

        when(recetaRepository.findAll()).thenReturn(recetasFicticias);

        List<Receta> resultado = recetaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(recetaRepository, times(1)).findAll();
    }
}