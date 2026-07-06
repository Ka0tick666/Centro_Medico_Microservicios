package com.example.ms_seguro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "cobertura")
public class Cobertura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cobertura")
    private Long idCobertura;

    @Column(name = "id_paciente")
    private Long idPaciente;

    private Double porcentaje;

    @ManyToOne
    @JoinColumn(name = "id_seguro", nullable = false)
    @JsonIgnore
    private Seguro seguro;

    // Constructor vacío obligatorio para JPA
    public Cobertura() {}

    // Getters y Setters manuales para asegurar la compilación
    public Long getIdCobertura() {
        return idCobertura;
    }

    public void setIdCobertura(Long idCobertura) {
        this.idCobertura = idCobertura;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }
}