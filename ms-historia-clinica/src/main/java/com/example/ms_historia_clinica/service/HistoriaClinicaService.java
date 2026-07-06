package com.example.ms_historia_clinica.service;

import com.example.ms_historia_clinica.model.HistoriaClinica;
import com.example.ms_historia_clinica.model.EntradaHistoria;
import com.example.ms_historia_clinica.model.HistoriaCompletaDTO;
import com.example.ms_historia_clinica.client.LaboratorioFeignClient;
import com.example.ms_historia_clinica.client.FarmaciaFeignClient;
import com.example.ms_historia_clinica.client.PacienteFeignClient;
import com.example.ms_historia_clinica.repository.HistoriaRepository;
import com.example.ms_historia_clinica.repository.EntradaHistoriaRepository;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaService {

    private final HistoriaRepository historiaRepository;
    private final EntradaHistoriaRepository entradaRepository;
    private final LaboratorioFeignClient laboratorioClient;
    private final FarmaciaFeignClient farmaciaClient;
    private final PacienteFeignClient pacienteClient;

    public HistoriaClinicaService(HistoriaRepository historiaRepository, 
                                  EntradaHistoriaRepository entradaRepository,
                                  LaboratorioFeignClient laboratorioClient, 
                                  FarmaciaFeignClient farmaciaClient,
                                  PacienteFeignClient pacienteClient) {
        this.historiaRepository = historiaRepository;
        this.entradaRepository = entradaRepository;
        this.laboratorioClient = laboratorioClient;
        this.farmaciaClient = farmaciaClient;
        this.pacienteClient = pacienteClient;
    }

    // 1. Lógica Orquestadora: Consume Feign para traer todo el ecosistema del paciente
    public Optional<HistoriaCompletaDTO> getHistoriaCompletaPorPaciente(Integer idPaciente) {
        Optional<HistoriaClinica> historiaOpt = historiaRepository.findByIdPaciente(idPaciente);
        
        // Creamos las listas y objetos vacíos por si los servicios externos fallan
        List<Object> examenes = new ArrayList<>();
        List<Object> recetas = new ArrayList<>();
        Object datosPaciente = null;
        
        // Intentamos llamar a ms-paciente (Puerto 8081)
        try {
            datosPaciente = pacienteClient.obtenerPacientePorId(idPaciente);
        } catch(Exception e) {
            /* Fallback en caso de error o servicio caído */
        }

        // Intentamos llamar a ms-laboratorio (Puerto 8086)
        try { 
            examenes = laboratorioClient.obtenerExamenesPorPaciente(idPaciente); 
        } catch(Exception e) { 
            /* Fallback en caso de error o servicio caído */ 
        }
        
        // Intentamos llamar a ms-farmacia (Puerto 8085)
        try { 
            recetas = farmaciaClient.obtenerRecetasPorPaciente(idPaciente); 
        } catch(Exception e) { 
            /* Fallback en caso de error o servicio caído */ 
        }
        
        if (historiaOpt.isPresent()) {
            HistoriaCompletaDTO dto = new HistoriaCompletaDTO(historiaOpt.get(), examenes, recetas);
            dto.setPaciente(datosPaciente);
            return Optional.of(dto);
        } else {
            // Si no existe la historia clínica local, devolvemos un objeto base con los datos externos encontrados
            HistoriaCompletaDTO dto = new HistoriaCompletaDTO(null, examenes, recetas);
            dto.setPaciente(datosPaciente);
            return Optional.of(dto);
        }
    }

    // 2. Traer solo los datos clínicos básicos
    public Optional<HistoriaClinica> getHistoriaBasePorPaciente(Integer idPaciente) {
        return historiaRepository.findByIdPaciente(idPaciente);
    }

    // 3. Listar todas las historias
    public List<HistoriaClinica> listarTodas() {
        return historiaRepository.findAll();
    }

    // 4. Buscar por ID de Historia
    public Optional<HistoriaClinica> buscarPorId(Integer id) {
        return historiaRepository.findById(id);
    }

    // 5. Filtrar por Grupo Sanguíneo
    public List<HistoriaClinica> buscarPorGrupoSanguineo(String grupo) {
        return historiaRepository.findByGrupoSanguineo(grupo);
    }

    // 6. Crear una nueva historia clínica
    public HistoriaClinica guardarHistoria(HistoriaClinica historia) {
        return historiaRepository.save(historia);
    }

    // 7. Agregar una nueva consulta/entrada a la historia
    public Optional<EntradaHistoria> agregarEntradaMedica(Integer idHistoria, EntradaHistoria entrada) {
        return historiaRepository.findById(idHistoria).map(historia -> {
            entrada.setHistoriaClinica(historia);
            return entradaRepository.save(entrada);
        });
    }

    // 8. Actualizar Antecedentes
    public Optional<HistoriaClinica> actualizarDatos(Integer id, HistoriaClinica datos) {
        return historiaRepository.findById(id).map(existente -> {
            existente.setGrupoSanguineo(datos.getGrupoSanguineo());
            existente.setAntecedentes(datos.getAntecedentes());
            return historiaRepository.save(existente);
        });
    }

    // 9. Eliminar historia clínica
    public boolean eliminarHistoriaCompleta(Integer id) {
        if (historiaRepository.existsById(id)) {
            historiaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 10. Eliminar entrada médica
    public boolean eliminarEntradaAislada(Integer idEntrada) {
        if (entradaRepository.existsById(idEntrada)) {
            entradaRepository.deleteById(idEntrada);
            return true;
        }
        return false;
    }
}