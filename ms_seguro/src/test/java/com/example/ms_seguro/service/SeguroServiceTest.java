package com.example.ms_seguro.service;

import com.example.ms_seguro.model.Seguro;
import com.example.ms_seguro.model.Cobertura;
import com.example.ms_seguro.repository.SeguroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeguroServiceTest {

    @Mock
    private SeguroRepository repository;

    @InjectMocks
    private SeguroService seguroService;

    private Seguro seguroMock;

    @BeforeEach
    void setUp() {
        // Inicializamos un objeto Seguro de prueba para los métodos
        seguroMock = new Seguro();
        seguroMock.setIdSeguro(1L);
        seguroMock.setNombreInstitucion("ISAPRE COLMENA");
        seguroMock.setTipoPlan("Plan Premium Gold");

        // Inicializamos una Cobertura asociada
        Cobertura coberturaMock = new Cobertura();
        coberturaMock.setIdCobertura(100L);
        coberturaMock.setIdPaciente(1L);
        coberturaMock.setPorcentaje(85.0);
        coberturaMock.setSeguro(seguroMock);

        List<Cobertura> listaCoberturas = new ArrayList<>();
        listaCoberturas.add(coberturaMock);
        seguroMock.setCoberturas(listaCoberturas);
    }

    @Test
    void testObtenerTodos() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.singletonList(seguroMock));

        // Act
        List<Seguro> resultado = seguroService.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ISAPRE COLMENA", resultado.get(0).getNombreInstitucion());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExitoso() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(seguroMock));

        // Act
        Seguro resultado = seguroService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSeguro());
        assertEquals("Plan Premium Gold", resultado.getTipoPlan());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Seguro resultado = seguroService.buscarPorId(99L);

        // Assert
        assertNull(resultado);
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void testGuardarSeguroEstableceRelacionBidireccional() {
        // Arrange
        when(repository.save(any(Seguro.class))).thenReturn(seguroMock);

        // Act
        Seguro guardado = seguroService.guardarSeguro(seguroMock);

        // Assert
        assertNotNull(guardado);
        assertNotNull(guardado.getCoberturas());
        // Comprobamos que se ejecuta el lazo forEach del service asignando el seguro a cada cobertura
        assertEquals(guardado, guardado.getCoberturas().get(0).getSeguro());
        verify(repository, times(1)).save(seguroMock);
    }
}