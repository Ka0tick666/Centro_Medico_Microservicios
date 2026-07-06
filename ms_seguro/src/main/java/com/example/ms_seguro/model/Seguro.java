package com.example.ms_seguro.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "seguro")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro")
    private Long idSeguro;

    @Column(name = "nombre_institucion")
    private String nombreInstitucion;

    @Column(name = "tipo_plan")
    private String tipoPlan;

    @OneToMany(mappedBy = "seguro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cobertura> coberturas;

    // Constructor vacío obligatorio para JPA
    public Seguro() {}

    // Getters y Setters manuales para evitar fallos de compilación
    public Long getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Long idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public List<Cobertura> getCoberturas() {
        return coberturas;
    }

    public void setCoberturas(List<Cobertura> coberturas) {
        this.coberturas = coberturas;
    }
}