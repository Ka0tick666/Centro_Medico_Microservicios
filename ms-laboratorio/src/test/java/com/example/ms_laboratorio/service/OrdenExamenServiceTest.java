package com.example.ms_laboratorio.service;

import com.example.ms_laboratorio.model.OrdenExamen;
import com.example.ms_laboratorio.repository.OrdenExamenRepository;
import com.example.ms_laboratorio.repository.ResultadoLaboratorioRepository;
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

public class OrdenExamenServiceTest {

    @Mock
    private OrdenExamenRepository ordenRepository;

    @Mock
    private ResultadoLaboratorioRepository resultadoRepository;

    @InjectMocks
    private OrdenExamenService ordenExamenService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testListarTodas() {
        List<OrdenExamen> ordenesFicticias = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OrdenExamen orden = new OrdenExamen();
            orden.setIdOrden(faker.number().randomDigit());
            orden.setIdPaciente(faker.number().randomDigit());
            orden.setIdMedico(faker.number().randomDigit());
            orden.setTipoExamen(faker.medical().medicineName() + " Analysis");
            orden.setFechaSolicitud(LocalDate.now());
            orden.setEstado(faker.options().option("Pendiente", "Finalizado"));
            ordenesFicticias.add(orden);
        }

        when(ordenRepository.findAll()).thenReturn(ordenesFicticias);

        List<OrdenExamen> resultado = ordenExamenService.listarTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(ordenRepository, times(1)).findAll();
    }
}