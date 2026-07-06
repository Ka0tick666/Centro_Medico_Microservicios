package com.example.ms_cita.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sobrecupos")
public class Sobrecupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSobrecupo;

    @Column(name = "id_cita_original", nullable = false)
    private Integer idCitaOriginal;

    @Column(name = "autorizado_por", nullable = false)
    private String autorizadoPor;

    @Column(name = "motivo_urgencia", nullable = false)
    private String motivoUrgencia;

    // --- GETTERS Y SETTERS ---

    public Integer getIdSobrecupo() { 
        return idSobrecupo; 
    }
    
    public void setIdSobrecupo(Integer idSobrecupo) { 
        this.idSobrecupo = idSobrecupo; 
    }

    public Integer getIdCitaOriginal() { 
        return idCitaOriginal; 
    }
    
    public void setIdCitaOriginal(Integer idCitaOriginal) { 
        this.idCitaOriginal = idCitaOriginal; 
    }

    public String getAutorizadoPor() { 
        return autorizadoPor; 
    }
    
    public void setAutorizadoPor(String autorizadoPor) { 
        this.autorizadoPor = autorizadoPor; 
    }

    public String getMotivoUrgencia() { 
        return motivoUrgencia; 
    }
    
    public void setMotivoUrgencia(String motivoUrgencia) { 
        this.motivoUrgencia = motivoUrgencia; 
    }
}