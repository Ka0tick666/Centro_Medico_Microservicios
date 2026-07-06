package com.example.ms_factura.service;

import com.example.ms_factura.model.Factura;
import com.example.ms_factura.repository.FacturaRepository;
import com.example.ms_factura.client.SeguroClient;
import com.example.ms_factura.client.NotificacionClient;
import com.example.ms_factura.client.MetricacentroClient; // <-- 1. Import correcto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository repository;

    @Autowired
    private SeguroClient seguroClient;

    @Autowired
    private NotificacionClient notificacionClient;

    @Autowired
    private MetricacentroClient metricacentroClient; // <-- 2. Inyección corregida (mismo nombre que usas abajo)

    public List<Factura> listarTodas() {
        return repository.findAll();
    }

    public Factura guardar(Factura factura) {
        // 1. Intercomunicación con ms_seguro: Validamos seguro
        if (factura.getIdSeguro() != null) {
            try {
                Object seguro = seguroClient.buscarPorId(factura.getIdSeguro());
                if (seguro != null) {
                    System.out.println("Microservicio Factura: El seguro existe. Procesando...");
                }
            } catch (Exception e) {
                System.out.println("No se pudo conectar con el servicio de Seguros o el ID no existe: " + e.getMessage());
            }
        }

        // Guardamos la factura en su propia base de datos
        Factura facturaGuardada = repository.save(factura);

        // 2. Intercomunicación con ms_metricacentro: Enviamos datos con tus atributos reales
        try {
            Map<String, Object> reqMetrica = new HashMap<>();
            // Usamos los nombres exactos de tu entidad Metricacentro
            reqMetrica.put("nombreMetricacentro", "Ingreso Comercial - Factura N° " + facturaGuardada.getIdFactura());
            reqMetrica.put("valor", facturaGuardada.getMonto());
            reqMetrica.put("fecha", LocalDate.now().toString());
            reqMetrica.put("idCentro", 1L); 
            
            // Usamos la variable inyectada arriba sin errores
            metricacentroClient.registrarMetrica(reqMetrica);
            System.out.println("Microservicio Factura: Datos estadísticos enviados con éxito a ms_metricacentro.");
        } catch (Exception e) {
            System.out.println("No se pudo registrar la métrica en el centro médico: " + e.getMessage());
        }

        // 3. Intercomunicación con ms_notificacion: Enviamos alerta si está PAGADA
        if ("PAGADA".equalsIgnoreCase(facturaGuardada.getEstado())) {
            try {
                Map<String, Object> reqNotificacion = new HashMap<>();
                reqNotificacion.put("idPaciente", facturaGuardada.getIdPaciente());
                reqNotificacion.put("mensaje", "Su factura N° " + facturaGuardada.getIdFactura() + " por un monto de $" + facturaGuardada.getMonto() + " ha sido PAGADA con éxito.");
                
                notificacionClient.crearNotificacion(reqNotificacion);
                System.out.println("Microservicio Factura: Alerta de pago enviada a ms_notificacion.");
            } catch (Exception e) {
                System.out.println("No se pudo enviar la notificación: " + e.getMessage());
            }
        }

        return facturaGuardada;
    }
}