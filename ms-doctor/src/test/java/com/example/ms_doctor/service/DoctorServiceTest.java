package com.example.ms_doctor.service;

import com.example.ms_doctor.model.Doctor;
import com.example.ms_doctor.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository repository;

    @InjectMocks
    private DoctorService service;

    @Test
    public void cuandoSeGuardaDoctor_DebeRetornarElDoctorGuardado() {
        // Arrange
        Doctor mockDoc = new Doctor();
        mockDoc.setNombres("Arturo");
        mockDoc.setApellidos("Vidal");
        mockDoc.setLicenciaMedica("MED-99999");
        mockDoc.setEspecialidad("Traumatología");
        mockDoc.setTelefono("+56988888888");
        mockDoc.setEmail("arturo@doctor.com");

        Mockito.when(repository.save(Mockito.any(Doctor.class))).thenReturn(mockDoc);

        // Act
        Doctor resultado = service.guardar(mockDoc);

        // Assert
        assertNotNull(resultado);
        assertEquals("Arturo", resultado.getNombres());
        assertEquals("MED-99999", resultado.getLicenciaMedica());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Doctor.class));
    }

    @Test
    public void cuandoSeBuscaPorLicenciaExistente_DebeRetornarElDoctor() {
        // Arrange
        Doctor mockDoc = new Doctor();
        mockDoc.setLicenciaMedica("MED-12345");
        mockDoc.setNombres("Michelle");

        Mockito.when(repository.findByLicenciaMedica("MED-12345")).thenReturn(Optional.of(mockDoc));

        // Act
        Optional<Doctor> resultado = service.buscarPorLicencia("MED-12345");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Michelle", resultado.get().getNombres());
        Mockito.verify(repository, Mockito.times(1)).findByLicenciaMedica("MED-12345");
    }

    @Test
    public void cuandoSeListanDoctores_DebeRetornarListaCompleta() {
        // Arrange
        Doctor d1 = new Doctor();
        d1.setNombres("Alexis");
        Doctor d2 = new Doctor();
        d2.setNombres("Claudio");

        List<Doctor> listaMock = Arrays.asList(d1, d2);
        Mockito.when(repository.findAll()).thenReturn(listaMock);

        // Act
        List<Doctor> resultado = service.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}