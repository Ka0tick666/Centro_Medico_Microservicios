package com.example.ms_cita.repository;

import com.example.ms_cita.model.Cita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CitaRepositoryTest {

    @Mock
    private CitaRepository repository;

    @Test
    public void cuandoSeRegistraUnaCita_DebeGuardarseCorrectamente() {
        // Arrange
        Cita mockCita = new Cita();
        mockCita.setIdPaciente(1);
        mockCita.setIdMedico(2);
        mockCita.setFecha(LocalDate.of(2026, 8, 20));
        mockCita.setHora(LocalTime.of(10, 30));
        mockCita.setEstado("PENDIENTE");
        mockCita.setMotivo("Control");

        Mockito.when(repository.save(Mockito.any(Cita.class))).thenReturn(mockCita);

        // Act
        Cita resultado = repository.save(mockCita);

        // Assert
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals(1, resultado.getIdPaciente());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Cita.class));
    }

    @Test
    public void cuandoSeBuscanCitasPorEstado_DebeRetornarListaFiltro() {
        // Arrange
        Cita c1 = new Cita();
        c1.setEstado("CANCELADA");
        List<Cita> listaMock = Arrays.asList(c1);

        Mockito.when(repository.findByEstadoIgnoreCase("CANCELADA")).thenReturn(listaMock);

        // Act
        List<Cita> resultado = repository.findByEstadoIgnoreCase("CANCELADA");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("CANCELADA", resultado.get(0).getEstado());
        Mockito.verify(repository, Mockito.times(1)).findByEstadoIgnoreCase("CANCELADA");
    }
}