package com.example.ms_farmacia.service;

import com.example.ms_farmacia.model.Receta;
import com.example.ms_farmacia.model.DetalleReceta;
import com.example.ms_farmacia.repository.RecetaRepository;
import com.example.ms_farmacia.repository.DetalleRecetaRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final DetalleRecetaRepository detalleRepository;

    public RecetaService(RecetaRepository recetaRepository, DetalleRecetaRepository detalleRepository) {
        this.recetaRepository = recetaRepository;
        this.detalleRepository = detalleRepository;
    }

    // 1. Obtener recetas de un paciente específico
    public List<Receta> obtenerPorPaciente(Integer idPaciente) {
        return recetaRepository.findByIdPaciente(idPaciente);
    }

    // 2. Listar todas las recetas del sistema
    public List<Receta> listarTodas() {
        return recetaRepository.findAll();
    }

    // 3. Buscar receta por ID primario
    public Optional<Receta> buscarPorId(Integer id) {
        return recetaRepository.findById(id);
    }

    // 4. Filtrar recetas por estado (Ej: "Pendiente")
    public List<Receta> listarPorEstado(String estado) {
        return recetaRepository.findByEstado(estado);
    }

    // 5. Filtrar recetas por ID del Médico emisor
    public List<Receta> listarPorMedico(Integer idMedico) {
        return recetaRepository.findByIdMedico(idMedico);
    }

    // 6. Crear una nueva receta básica
    public Receta registrarReceta(Receta receta) {
        if (receta.getFechaEmision() == null) {
            receta.setFechaEmision(LocalDate.now());
        }
        if (receta.getEstado() == null) {
            receta.setEstado("Pendiente");
        }
        return recetaRepository.save(receta);
    }

    // 7. Agregar un medicamento/detalle a una receta existente
    public Optional<DetalleReceta> agregarMedicamento(Integer idReceta, DetalleReceta detalle) {
        return recetaRepository.findById(idReceta).map(receta -> {
            detalle.setReceta(receta);
            return detalleRepository.save(detalle);
        });
    }

    // 8. Actualizar estado de la receta (Ej: Cambiar a "Entregado")
    public Optional<Receta> actualizarEstado(Integer id, String nuevoEstado) {
        return recetaRepository.findById(id).map(receta -> {
            receta.setEstado(nuevoEstado);
            return recetaRepository.save(receta);
        });
    }

    // 9. Eliminar una receta completa (con sus detalles en cascada)
    public boolean eliminarRecetaCompleta(Integer id) {
        if (recetaRepository.existsById(id)) {
            recetaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 10. Eliminar un medicamento específico de una receta
    public boolean eliminarMedicamentoAislado(Integer idDetalle) {
        if (detalleRepository.existsById(idDetalle)) {
            detalleRepository.deleteById(idDetalle);
            return true;
        }
        return false;
    }
}