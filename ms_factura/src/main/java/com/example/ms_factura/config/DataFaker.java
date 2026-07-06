package com.example.ms_factura.config;

import com.example.ms_factura.model.Factura;
import com.example.ms_factura.repository.FacturaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataFaker implements CommandLineRunner {

    private final FacturaRepository facturaRepository;

    public DataFaker(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("⏳ [DataFaker] Verificando estado de la tabla factura...");
        
        if (facturaRepository.count() == 0) {
            System.out.println("⏳ [DataFaker] Tabla vacía. Iniciando poblamiento automático...");

            Factura f1 = new Factura();
            f1.setIdPaciente(1L);
            f1.setIdSeguro(100L);
            f1.setMonto(45000.0);
            f1.setFechaEmision(LocalDate.now().minusDays(5));
            f1.setEstado("PENDIENTE");
            facturaRepository.save(f1);

            Factura f2 = new Factura();
            f2.setIdPaciente(1L);
            f2.setIdSeguro(100L);
            f2.setMonto(12500.0);
            f2.setFechaEmision(LocalDate.now().minusDays(2));
            f2.setEstado("PAGADA");
            facturaRepository.save(f2);

            Factura f3 = new Factura();
            f3.setIdPaciente(2L);
            f3.setIdSeguro(200L);
            f3.setMonto(89000.0);
            f3.setFechaEmision(LocalDate.now().minusDays(1));
            f3.setEstado("PENDIENTE");
            facturaRepository.save(f3);

            System.out.println("✅ [DataFaker] Base de datos poblada con éxito.");
        } else {
            System.out.println("ℹ️ [DataFaker] La tabla ya cuenta con registros. Omitiendo.");
        }
    }
}