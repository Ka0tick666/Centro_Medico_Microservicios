package com.example.ms_paciente.service;

import com.example.ms_paciente.model.Paciente;
import com.example.ms_paciente.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    @Test
    public void cuandoSeGuardaPaciente_DebeRetornarElPacienteGuardado() {
        Paciente mockPaciente = new Paciente();
        mockPaciente.setTipoDocumento("RUN");
        mockPaciente.setNumeroDocumento("19888777-6");
        mockPaciente.setNombres("Carlos");
        mockPaciente.setApellidos("Mendoza");
        mockPaciente.setFechaNacimiento(LocalDate.of(1995, 5, 12));
        mockPaciente.setGenero("Masculino");
        mockPaciente.setEmail("carlos@test.com");
        mockPaciente.setTelefono("+56912345678");
        mockPaciente.setDireccion("Av. Concha y Toro 123");
        mockPaciente.setRut("19888777-6");

        // Usamos Mockito.any() explícito para que el IDE sepa exactamente de dónde viene
        Mockito.when(repository.save(Mockito.any(Paciente.class))).thenReturn(mockPaciente);

        Paciente resultado = service.guardar(mockPaciente);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombres());
        // Verificamos de forma directa y calificada
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Paciente.class));
    }

    @Test
    public void cuandoSeBuscaPorRutExistente_DebeRetornarElPaciente() {
        Paciente mockPaciente = new Paciente();
        mockPaciente.setTipoDocumento("RUN");
        mockPaciente.setNumeroDocumento("12345678-9");
        mockPaciente.setRut("12345678-9");
        mockPaciente.setNombres("Ana");
        mockPaciente.setApellidos("Silva");
        mockPaciente.setFechaNacimiento(LocalDate.of(1990, 8, 20));
        mockPaciente.setGenero("Femenino");
        mockPaciente.setEmail("ana@test.com");

        Mockito.when(repository.findByRut("12345678-9")).thenReturn(Optional.of(mockPaciente));

        Optional<Paciente> resultado = service.buscarPorRut("12345678-9");

        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombres());
        Mockito.verify(repository, Mockito.times(1)).findByRut("12345678-9");
    }
}