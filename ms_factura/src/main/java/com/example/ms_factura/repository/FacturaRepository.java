package com.example.ms_factura.repository;

import com.example.ms_factura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    // 1. Buscar todas las facturas de un paciente específico
    @Query("SELECT f FROM Factura f WHERE f.idPaciente = :idPaciente")
    List<Factura> buscarPorPaciente(@Param("idPaciente") Long idPaciente);

    // 2. Buscar facturas por estado (ej: "PENDIENTE", "PAGADA")
    @Query("SELECT f FROM Factura f WHERE f.estado = :estado")
    List<Factura> buscarPorEstado(@Param("estado") String estado);

    // 3. Buscar facturas de un paciente que tengan un estado específico
    @Query("SELECT f FROM Factura f WHERE f.idPaciente = :idPaciente AND f.estado = :estado")
    List<Factura> buscarPorPacienteYEstado(@Param("idPaciente") Long idPaciente, @Param("estado") String estado);

    // 4. Buscar facturas asociadas a un seguro de salud específico
    @Query("SELECT f FROM Factura f WHERE f.idSeguro = :idSeguro")
    List<Factura> buscarPorSeguro(@Param("idSeguro") Long idSeguro);

    // 5. Buscar facturas emitidas en una fecha exacta
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision = :fecha")
    List<Factura> buscarPorFechaExacta(@Param("fecha") LocalDate fecha);

    // 6. Buscar facturas dentro de un rango de fechas (ideal para cierres de mes)
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :inicio AND :fin")
    List<Factura> buscarPorRangoFechas(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    // 7. Buscar facturas cuyo monto sea mayor o igual a un valor determinado
    @Query("SELECT f FROM Factura f WHERE f.monto >= :monto")
    List<Factura> buscarPorMontoMayorOIgual(@Param("monto") Double monto);

    // 8. Seleccionar montos de facturas pagadas (Evitamos el SUM directo en el arranque para prevenir el Error 500 si la BD está vacía)
    @Query("SELECT f.monto FROM Factura f WHERE f.estado = 'PAGADA'")
    List<Double> listarMontosPagados();

    // 9. Contar cuántas facturas pendientes tiene un paciente
    @Query("SELECT COUNT(f) FROM Factura f WHERE f.idPaciente = :idPaciente AND f.estado = 'PENDIENTE'")
    Long contarPendientesPorPaciente(@Param("idPaciente") Long idPaciente);

    // 10. Listar todas las facturas ordenadas por monto de mayor a menor
    @Query("SELECT f FROM Factura f ORDER BY f.monto DESC")
    List<Factura> listarOrdenadasPorMontoDesc();
}