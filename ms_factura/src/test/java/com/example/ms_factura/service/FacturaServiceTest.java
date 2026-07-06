package com.example.ms_factura.service;

import com.example.ms_factura.client.MetricacentroClient;
import com.example.ms_factura.client.NotificacionClient;
import com.example.ms_factura.client.SeguroClient;
import com.example.ms_factura.model.Factura;
import com.example.ms_factura.repository.FacturaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FacturaServiceTest {

    @Mock
    private FacturaRepository repository;

    @Mock
    private SeguroClient seguroClient;

    @Mock
    private NotificacionClient notificacionClient;

    @Mock
    private MetricacentroClient metricacentroClient;

    @InjectMocks
    private FacturaService facturaService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testListarTodas() {
        List<Factura> facturasFicticias = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Factura f = new Factura();
            f.setIdFactura(faker.number().randomNumber());
            f.setIdPaciente(faker.number().randomNumber());
            f.setMonto(faker.number().randomDouble(2, 10000, 150000));
            f.setFechaEmision(LocalDate.now());
            f.setEstado(faker.options().option("PENDIENTE", "PAGADA"));
            facturasFicticias.add(f);
        }

        when(repository.findAll()).thenReturn(facturasFicticias);

        List<Factura> resultado = facturaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGuardarFacturaPagada() {
        Factura facturaEntrada = new Factura();
        facturaEntrada.setIdPaciente(faker.number().randomNumber());
        facturaEntrada.setIdSeguro(faker.number().randomNumber());
        facturaEntrada.setMonto(faker.number().randomDouble(2, 5000, 50000));
        facturaEntrada.setFechaEmision(LocalDate.now());
        facturaEntrada.setEstado("PAGADA");

        Factura facturaGuardada = new Factura();
        facturaGuardada.setIdFactura(1L);
        facturaGuardada.setIdPaciente(facturaEntrada.getIdPaciente());
        facturaGuardada.setIdSeguro(facturaEntrada.getIdSeguro());
        facturaGuardada.setMonto(facturaEntrada.getMonto());
        facturaGuardada.setFechaEmision(facturaEntrada.getFechaEmision());
        facturaGuardada.setEstado("PAGADA");

        when(seguroClient.buscarPorId(any(Long.class))).thenReturn(new Object());
        when(repository.save(any(Factura.class))).thenReturn(facturaGuardada);
        when(metricacentroClient.registrarMetrica(any())).thenReturn(new Object());
        when(notificacionClient.crearNotificacion(any())).thenReturn(new Object());

        Factura resultado = facturaService.guardar(facturaEntrada);

        assertNotNull(resultado);
        assertEquals("PAGADA", resultado.getEstado());
        assertEquals(1L, resultado.getIdFactura());
        
        verify(repository, times(1)).save(any(Factura.class));
        verify(notificacionClient, times(1)).crearNotificacion(any());
    }
}