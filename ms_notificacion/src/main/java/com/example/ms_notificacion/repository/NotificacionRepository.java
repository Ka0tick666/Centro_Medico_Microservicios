package com.example.ms_notificacion.repository;

import com.example.ms_notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // 1. Buscar todas las notificaciones de un paciente específico
    @Query("SELECT n FROM Notificacion n WHERE n.idPaciente = :idPaciente")
    List<Notificacion> buscarPorPaciente(@Param("idPaciente") Long idPaciente);

    // 2. Buscar notificaciones por su estado (ej: "PENDIENTE", "ENVIADO")
    @Query("SELECT n FROM Notificacion n WHERE n.estado = :estado")
    List<Notificacion> buscarPorEstado(@Param("estado") String estado);

    // 3. Buscar notificaciones de un paciente que estén en un estado específico
    @Query("SELECT n FROM Notificacion n WHERE n.idPaciente = :idPaciente AND n.estado = :estado")
    List<Notificacion> buscarPorPacienteYEstado(@Param("idPaciente") Long idPaciente, @Param("estado") String estado);

    // 4. Buscar notificaciones que contengan una palabra clave en el mensaje (ej: "urgente", "pago")
    @Query("SELECT n FROM Notificacion n WHERE n.mensaje LIKE %:palabra%")
    List<Notificacion> buscarPorMensajeContiene(@Param("palabra") String palabra);

    // 5. Buscar las notificaciones enviadas después de una fecha y hora específica
    @Query("SELECT n FROM Notificacion n WHERE n.fechaEnvio > :fecha")
    List<Notificacion> buscarEnviadasDespuesDe(@Param("fecha") LocalDateTime fecha);

    // 6. Buscar notificaciones entre un rango de fechas y horas (Ideal para reportes diarios)
    @Query("SELECT n FROM Notificacion n WHERE n.fechaEnvio BETWEEN :inicio AND :fin")
    List<Notificacion> buscarPorRangoDeFechas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // 7. Listar todas las notificaciones ordenadas de la más reciente a la más antigua
    @Query("SELECT n FROM Notificacion n ORDER BY n.fechaEnvio DESC")
    List<Notificacion> listarOrdenadasPorFechaDesc();

    // 8. Contar cuántas notificaciones fallidas ("FALLIDO") tiene un paciente
    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.idPaciente = :idPaciente AND n.estado = 'FALLIDO'")
    Long contarNotificacionesFallidasDePaciente(@Param("idPaciente") Long idPaciente);

    // 9. Contar el total de notificaciones acumuladas en un estado en particular
    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.estado = :estado")
    Long contarTotalPorEstado(@Param("estado") String estado);

    // 10. Traer las últimas 5 notificaciones de un paciente (Usa orden desc por ID o fecha)
    @Query("SELECT n FROM Notificacion n WHERE n.idPaciente = :idPaciente ORDER BY n.idNotificacion DESC")
    List<Notificacion> buscarUltimasPorPaciente(@Param("idPaciente") Long idPaciente);
}