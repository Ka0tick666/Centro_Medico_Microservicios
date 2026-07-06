package com.example.ms_historia_clinica.model;

import java.util.List;

public class HistoriaCompletaDTO {
    private HistoriaClinica historiaBase;
    private List<Object> examenesLaboratorio; // Recibe el JSON del microservicio 8086
    private List<Object> recetasFarmacia;      // Recibe el JSON del microservicio 8085
    private Object paciente;                   // Recibe el JSON del microservicio 8081

    public HistoriaCompletaDTO() {}

    public HistoriaCompletaDTO(HistoriaClinica historiaBase, List<Object> examenesLaboratorio, List<Object> recetasFarmacia) {
        this.historiaBase = historiaBase;
        this.examenesLaboratorio = examenesLaboratorio;
        this.recetasFarmacia = recetasFarmacia;
    }

    // Getters y Setters
    public HistoriaClinica getHistoriaBase() { return historiaBase; }
    public void setHistoriaBase(HistoriaClinica historiaBase) { this.historiaBase = historiaBase; }

    public List<Object> getExamenesLaboratorio() { return examenesLaboratorio; }
    public void setExamenesLaboratorio(List<Object> examenesLaboratorio) { this.examenesLaboratorio = examenesLaboratorio; }

    public List<Object> getRecetasFarmacia() { return recetasFarmacia; }
    public void setRecetasFarmacia(List<Object> recetasFarmacia) { this.recetasFarmacia = recetasFarmacia; }

    public Object getPaciente() { return paciente; }
    public void setPaciente(Object paciente) { this.paciente = paciente; }
}