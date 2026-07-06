package com.example.ms_notificacion.service;

import com.example.ms_notificacion.model.Notificacion;
import com.example.ms_notificacion.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionMock;

    @BeforeEach
    void setUp() {
        notificacionMock = new Notificacion();
        notificacionMock.setIdNotificacion(1L);
        notificacionMock.setIdPaciente(10L);
        notificacionMock.setMensaje("Estimado paciente, su examen está listo.");
        notificacionMock.setFechaEnvio(LocalDateTime.now());
        notificacionMock.setEstado("ENVIADO");
    }

    @Test
    void testListarTodas() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(notificacionMock));

        // Act
        List<Notificacion> resultado = notificacionService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ENVIADO", resultado.get(0).getEstado());
        assertEquals(10L, resultado.get(0).getIdPaciente());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGuardarNotificacionExitosa() {
        // Arrange
        when(repository.save(any(Notificacion.class))).thenReturn(notificacionMock);

        // Act
        Notificacion guardada = notificacionService.guardar(notificacionMock);

        // Assert
        assertNotNull(guardada);
        assertEquals(1L, guardada.getIdNotificacion());
        assertEquals("Estimado paciente, su examen está listo.", guardada.getMensaje());
        verify(repository, times(1)).save(notificacionMock);
    }
}