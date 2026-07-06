package com.example.ms_laboratorio.service;

import com.example.ms_laboratorio.model.OrdenExamen;
import com.example.ms_laboratorio.model.ResultadoLaboratorio;
import com.example.ms_laboratorio.repository.OrdenExamenRepository;
import com.example.ms_laboratorio.repository.ResultadoLaboratorioRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenExamenService {

    private final OrdenExamenRepository ordenRepository;
    private final ResultadoLaboratorioRepository resultadoRepository;

    public OrdenExamenService(OrdenExamenRepository ordenRepository, ResultadoLaboratorioRepository resultadoRepository) {
        this.ordenRepository = ordenRepository;
        this.resultadoRepository = resultadoRepository;
    }

    // 1. Obtener órdenes con sus resultas por paciente
    public List<OrdenExamen> obtenerPorPaciente(Integer idPaciente) {
        return ordenRepository.findByIdPaciente(idPaciente);
    }

    // 2. Listar todas las órdenes registradas
    public List<OrdenExamen> listarTodas() {
        return ordenRepository.findAll();
    }

    // 3. Buscar una orden específica por ID
    public Optional<OrdenExamen> buscarPorId(Integer id) {
        return ordenRepository.findById(id);
    }

    // 4. Filtrar órdenes que estén "Pendiente"
    public List<OrdenExamen> listarPendientes() {
        return ordenRepository.findByEstado("Pendiente");
    }

    // 5. Filtrar por tipo de examen clínico
    public List<OrdenExamen> buscarPorTipoExamen(String tipo) {
        return ordenRepository.findByTipoExamenContainingIgnoreCase(tipo);
    }

    // 6. Registrar una nueva orden médica
    public OrdenExamen registrarOrden(OrdenExamen orden) {
        if (orden.getFechaSolicitud() == null) {
            orden.setFechaSolicitud(LocalDate.now());
        }
        if (orden.getEstado() == null) {
            orden.setEstado("Pendiente");
        }
        return ordenRepository.save(orden);
    }

    // 7. Agregar un resultado analítico a una orden (Mapeando a la lista 'resultas')
    public Optional<ResultadoLaboratorio> registrarResultado(Integer idOrden, ResultadoLaboratorio resultado) {
        return ordenRepository.findById(idOrden).map(orden -> {
            resultado.setOrdenExamen(orden); // Vincula la llave foránea
            if (resultado.getFechaResultado() == null) {
                resultado.setFechaResultado(LocalDate.now());
            }
            orden.setEstado("Finalizado"); // Actualiza automáticamente el estado de la orden
            ordenRepository.save(orden);
            return resultadoRepository.save(resultado);
        });
    }

    // 8. Modificar manualmente el estado de una orden
    public Optional<OrdenExamen> actualizarEstado(Integer id, String nuevoEstado) {
        return ordenRepository.findById(id).map(orden -> {
            orden.setEstado(nuevoEstado);
            return ordenRepository.save(orden);
        });
    }

    // 9. Eliminar una orden por completo con sus resultas en cascada
    public boolean eliminarOrden(Integer id) {
        if (ordenRepository.existsById(id)) {
            ordenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 10. Eliminar un resultado analítico aislado
    public boolean eliminarResultado(Integer idResultado) {
        if (resultadoRepository.existsById(idResultado)) {
            resultadoRepository.deleteById(idResultado);
            return true;
        }
        return false;
    }
}
