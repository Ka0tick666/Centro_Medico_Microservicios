package com.example.ms_metricacentro.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "metricacentro")
public class Metricacentro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metrica")
    private Long idMetrica;

    @Column(name = "nombre_metricacentro")
    private String nombreMetricacentro;

    private Double valor;

    private LocalDate fecha;

    @Column(name = "id_centro")
    private Long idCentro;

    // Constructor vacío obligatorio para JPA
    public Metricacentro() {}

    // Getters y Setters Manuales para evitar fallos con Maven Compiler
    public Long getIdMetrica() {
        return idMetrica;
    }

    public void setIdMetrica(Long idMetrica) {
        this.idMetrica = idMetrica;
    }

    public String getNombreMetricacentro() {
        return nombreMetricacentro;
    }

    public void setNombreMetricacentro(String nombreMetricacentro) {
        this.nombreMetricacentro = nombreMetricacentro;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }
}