package com.example.ms_metricacentro.service;

import com.example.ms_metricacentro.model.Metricacentro;
import com.example.ms_metricacentro.repository.MetricacentroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MetricacentroServiceTest {

    @Mock
    private MetricacentroRepository repository;

    @InjectMocks
    private MetricacentroService metricacentroService;

    private Metricacentro metricaMock;

    @BeforeEach
    void setUp() {
        metricaMock = new Metricacentro();
        metricaMock.setIdMetrica(1L);
        metricaMock.setNombreMetricacentro("Nueva Receta Emitida");
        metricaMock.setValor(1.0);
        metricaMock.setFecha(LocalDate.now());
        metricaMock.setIdCentro(1L);
    }

    @Test
    void testListarTodas() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.singletonList(metricaMock));

        // Act
        List<Metricacentro> resultado = metricacentroService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Nueva Receta Emitida", resultado.get(0).getNombreMetricacentro());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGuardarMetricaExitosa() {
        // Arrange
        when(repository.save(any(Metricacentro.class))).thenReturn(metricaMock);

        // Act
        Metricacentro guardada = metricacentroService.guardar(metricaMock);

        // Assert
        assertNotNull(guardada);
        assertEquals(1L, guardada.getIdMetrica());
        assertEquals(1.0, guardada.getValor());
        verify(repository, times(1)).save(metricaMock);
    }
}