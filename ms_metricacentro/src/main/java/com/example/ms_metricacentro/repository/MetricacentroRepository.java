package com.example.ms_metricacentro.repository;

import com.example.ms_metricacentro.model.Metricacentro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MetricacentroRepository extends JpaRepository<Metricacentro, Long> {

    // 1. Buscar todas las métricas de un centro médico específico
    @Query("SELECT m FROM Metricacentro m WHERE m.idCentro = :idCentro")
    List<Metricacentro> buscarPorCentro(@Param("idCentro") Long idCentro);

    // 2. CORREGIDO: m.nombreMetricacentro
    @Query("SELECT m FROM Metricacentro m WHERE m.nombreMetricacentro = :nombre")
    List<Metricacentro> buscarPorNombreMetrica(@Param("nombre") String nombre);

    // 3. CORREGIDO: m.nombreMetricacentro
    @Query("SELECT m FROM Metricacentro m WHERE m.idCentro = :idCentro AND m.nombreMetricacentro = :nombre")
    List<Metricacentro> buscarPorCentroYNombre(@Param("idCentro") Long idCentro, @Param("nombre") String nombre);

    // 4. Buscar métricas registradas en una fecha exacta
    @Query("SELECT m FROM Metricacentro m WHERE m.fecha = :fecha")
    List<Metricacentro> buscarPorFechaExacta(@Param("fecha") LocalDate fecha);

    // 5. Buscar métricas cuyo valor sea mayor o igual a un límite (ej: alertas de tiempos altos)
    @Query("SELECT m FROM Metricacentro m WHERE m.valor >= :limite")
    List<Metricacentro> buscarPorValorMayorOIgual(@Param("limite") Double limite);

    // 6. Buscar métricas de un centro registradas entre un rango de fechas
    @Query("SELECT m FROM Metricacentro m WHERE m.idCentro = :idCentro AND m.fecha BETWEEN :inicio AND :fin")
    List<Metricacentro> buscarPorCentroYRangoFechas(@Param("idCentro") Long idCentro, @Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    // 7. Listar todas las métricas ordenadas por su valor de forma descendente (de mayor a menor)
    @Query("SELECT m FROM Metricacentro m ORDER BY m.valor DESC")
    List<Metricacentro> listarOrdenadasPorValorDesc();

    // 8. Listar las métricas de un centro específico ordenadas de la más reciente a la más antigua
    @Query("SELECT m FROM Metricacentro m WHERE m.idCentro = :idCentro ORDER BY m.fecha DESC")
    List<Metricacentro> listarPorCentroOrdenadasPorFechaDesc(@Param("idCentro") Long idCentro);

    // 9. Contar cuántos registros de métricas tiene un centro en particular
    @Query("SELECT COUNT(m) FROM Metricacentro m WHERE m.idCentro = :idCentro")
    Long contarMetricasPorCentro(@Param("idCentro") Long idCentro);

    // 10. CORREGIDO: m.nombreMetricacentro
    @Query("SELECT MAX(m.valor) FROM Metricacentro m WHERE m.nombreMetricacentro = :nombre")
    Double obtenerValorMaximoPorNombreMetrica(@Param("nombre") String nombre);
}