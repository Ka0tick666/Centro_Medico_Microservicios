package com.example.ms_seguro.repository;

import com.example.ms_seguro.model.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {

    // 1. Buscar seguros por el nombre exacto de la institución (ej: "FONASA")
    @Query("SELECT s FROM Seguro s WHERE s.nombreInstitucion = :nombre")
    List<Seguro> buscarPorNombreInstitucion(@Param("nombre") String nombre);

    // 2. Buscar seguros cuyo nombre de institución contenga una palabra (Filtro flexible)
    @Query("SELECT s FROM Seguro s WHERE s.nombreInstitucion LIKE %:palabra%")
    List<Seguro> buscarPorNombreContiene(@Param("palabra") String palabra);

    // 3. Buscar seguros por el tipo de plan exacto (ej: "Premium", "Básico")
    @Query("SELECT s FROM Seguro s WHERE s.tipoPlan = :tipoPlan")
    List<Seguro> buscarPorTipoPlan(@Param("tipoPlan") String tipoPlan);

    // 4. Buscar seguros que tengan tanto un nombre como un tipo de plan específico
    @Query("SELECT s FROM Seguro s WHERE s.nombreInstitucion = :nombre AND s.tipoPlan = :tipoPlan")
    List<Seguro> buscarPorNombreYPlan(@Param("nombre") String nombre, @Param("tipoPlan") String tipoPlan);

    // 5. Listar seguros ordenados alfabéticamente por el nombre de la institución
    @Query("SELECT s FROM Seguro s ORDER BY s.nombreInstitucion ASC")
    List<Seguro> listarOrdenadosPorNombreAsc();

    // 6. Buscar seguros que tengan asociada al menos una cobertura para un ID de paciente específico
    @Query("SELECT DISTINCT s FROM Seguro s JOIN s.coberturas c WHERE c.idPaciente = :idPaciente")
    List<Seguro> buscarSegurosPorIdPaciente(@Param("idPaciente") Long idPaciente);

    // 7. Buscar seguros donde alguna de sus coberturas tenga un porcentaje mayor o igual a X
    @Query("SELECT DISTINCT s FROM Seguro s JOIN s.coberturas c WHERE c.porcentaje >= :porcentaje")
    List<Seguro> buscarSegurosConCoberturaAlta(@Param("porcentaje") Double porcentaje);

    // 8. Contar cuántos seguros registrados pertenecen a un tipo de plan específico
    @Query("SELECT COUNT(s) FROM Seguro s WHERE s.tipoPlan = :tipoPlan")
    Long contarSegurosPorTipoPlan(@Param("tipoPlan") String tipoPlan);

    // 9. Verificar si existe algún seguro registrado para una institución médica específica
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seguro s WHERE s.nombreInstitucion = :nombre")
    boolean existeInstitucion(@Param("nombre") String nombre);

    // 10. Traer los seguros que NO tengan coberturas asignadas todavía (Seguros nuevos o vacíos)
    @Query("SELECT s FROM Seguro s WHERE s.coberturas IS EMPTY")
    List<Seguro> buscarSegurosSinCoberturas();
}