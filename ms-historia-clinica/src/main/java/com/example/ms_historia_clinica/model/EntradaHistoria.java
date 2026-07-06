package com.example.ms_historia_clinica.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "entrada_historia")
public class EntradaHistoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada")
    private Integer idEntrada;

    @Column(name = "id_cita", nullable = false)
    private Integer idCita;

    @Column(name = "detalle_consulta", columnDefinition = "TEXT", nullable = false)
    private String detalleConsulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historia", nullable = false)
    @JsonIgnore
    private HistoriaClinica historiaClinica;

    @OneToMany(mappedBy = "entradaHistoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiagnosticoCIE10> diagnosticos;

    // --- GETTERS Y SETTERS ---
    public Integer getIdEntrada() { return idEntrada; }
    public void setIdEntrada(Integer idEntrada) { this.idEntrada = idEntrada; }

    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public String getDetalleConsulta() { return detalleConsulta; }
    public void setDetalleConsulta(String detalleConsulta) { this.detalleConsulta = detalleConsulta; }

    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }

    public List<DiagnosticoCIE10> getDiagnosticos() { return diagnosticos; }
    public void setDiagnosticos(List<DiagnosticoCIE10> diagnosticos) { this.diagnosticos = diagnosticos; }
}