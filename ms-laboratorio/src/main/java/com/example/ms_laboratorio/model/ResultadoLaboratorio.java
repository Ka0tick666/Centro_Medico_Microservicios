package com.example.ms_laboratorio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resultado_laboratorio")
public class ResultadoLaboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Integer idResultado;

    @Column(name = "valor", length = 50)
    private String valor;

    @Column(name = "rango_referencia", length = 50)
    private String rangoReferencia;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_resultado")
    private LocalDate fechaResultado;

    // Conexión del lado hijo hacia la orden contenedora
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false)
    @JsonBackReference // Evita que se repita la orden dentro de cada resultado al generar el JSON
    private OrdenExamen ordenExamen;

    // Constructores
    public ResultadoLaboratorio() {}

    // Getters y Setters
    public Integer getIdResultado() { return idResultado; }
    public void setIdResultado(Integer idResultado) { this.idResultado = idResultado; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getRangoReferencia() { return rangoReferencia; }
    public void setRangoReferencia(String rangoReferencia) { this.rangoReferencia = rangoReferencia; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public LocalDate getFechaResultado() { return fechaResultado; }
    public void setFechaResultado(LocalDate fechaResultado) { this.fechaResultado = fechaResultado; }

    public OrdenExamen getOrdenExamen() { return ordenExamen; }
    public void setOrdenExamen(OrdenExamen ordenExamen) { this.ordenExamen = ordenExamen; }
}