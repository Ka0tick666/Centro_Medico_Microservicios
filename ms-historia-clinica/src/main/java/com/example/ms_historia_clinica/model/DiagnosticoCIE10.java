package com.example.ms_historia_clinica.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "diagnostico_cie10")
public class DiagnosticoCIE10 {

    @Id
    @Column(name = "codigo_cie10", length = 10)
    private String codigoCie10;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrada", nullable = false)
    @JsonIgnore
    private EntradaHistoria entradaHistoria;

    // --- GETTERS Y SETTERS ---
    public String getCodigoCie10() { return codigoCie10; }
    public void setCodigoCie10(String codigoCie10) { this.codigoCie10 = codigoCie10; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EntradaHistoria getEntradaHistoria() { return entradaHistoria; }
    public void setEntradaHistoria(EntradaHistoria entradaHistoria) { this.entradaHistoria = entradaHistoria; }
}