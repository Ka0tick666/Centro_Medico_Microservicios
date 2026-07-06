package com.example.ms_notificacion.config;

import com.example.ms_notificacion.model.Notificacion;
import com.example.ms_notificacion.repository.NotificacionRepository;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Configuration
public class DataSetupConfig {

    private final NotificacionRepository notificacionRepository;
    private final EntityManager entityManager;

    public DataSetupConfig(NotificacionRepository notificacionRepository, EntityManager entityManager) {
        this.notificacionRepository = notificacionRepository;
        this.entityManager = entityManager;
    }

    @Bean
    @Transactional
    public CommandLineRunner poblarNotificaciones() {
        return args -> {
            // Verificamos si la tabla de bitácora está vacía
            Long totalNotificaciones = (Long) entityManager.createQuery("SELECT COUNT(n) FROM Notificacion n").getSingleResult();

            if (totalNotificaciones == 0) {
                System.out.println("⏳ [DataFaker] Bitácora de notificaciones vacía. Iniciando poblamiento automático...");
                
                Faker faker = new Faker(new Locale("es"));
                
                String[] mensajesEjemplo = {
                    "Estimado paciente, recuerde su cita médica para mañana a las 10:00 hrs.",
                    "Su receta médica en ms_farmacia ya se encuentra disponible para retiro.",
                    "Alerta: Sus resultados de exámenes de laboratorio ya fueron cargados al sistema.",
                    "Confirmación de pago recibida. Su bono médico fue emitido con éxito.",
                    "Recordatorio: Control preventivo anual disponible con cobertura de su Isapre/Fonasa."
                };
                
                String[] estados = {"ENVIADO", "PENDIENTE", "FALLIDO"};

                // Creamos 10 notificaciones aleatorias en el historial base
                for (int i = 0; i < 10; i++) {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setIdPaciente((long) faker.random().nextInt(1, 50));
                    notificacion.setMensaje(mensajesEjemplo[faker.random().nextInt(mensajesEjemplo.length)]);
                    // Genera una fecha/hora aleatoria de los últimos 5 días
                    notificacion.setFechaEnvio(LocalDateTime.now().minusDays(faker.random().nextInt(0, 5)).minusHours(faker.random().nextInt(1, 23)));
                    notificacion.setEstado(estados[faker.random().nextInt(estados.length)]);
                    
                    notificacionRepository.save(notificacion);
                }

                System.out.println("✅ [DataFaker] Historial inicial de alertas inyectado correctamente en ms_notificacion.");
            } else {
                System.out.println("ℹ️ [DataFaker] La base de datos de ms_notificacion ya contiene registros. Omitiendo inyección.");
            }
        };
    }
}