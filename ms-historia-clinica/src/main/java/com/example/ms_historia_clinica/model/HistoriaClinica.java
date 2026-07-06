package com.example.ms_historia_clinica.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historia")
    private Integer idHistoria;

    @Column(name = "id_paciente", nullable = false, unique = true)
    private Integer idPaciente;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(columnDefinition = "TEXT")
    private String antecedentes;

    @Column(name = "grupo_sanguineo", length = 15)
    private String grupoSanguineo;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EntradaHistoria> entradas;

    // --- GETTERS Y SETTERS EXACTOS ---
    public Integer getIdHistoria() { 
        return idHistoria; 
    }
    public void setIdHistoria(Integer idHistoria) { 
        this.idHistoria = idHistoria; 
    }

    public Integer getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getAntecedentes() { return antecedentes; }
    public void setAntecedentes(String antecedentes) { this.antecedentes = antecedentes; }

    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

    public List<EntradaHistoria> getEntradas() { return entradas; }
    public void setEntradas(List<EntradaHistoria> entradas) { this.entradas = entradas; }
}
