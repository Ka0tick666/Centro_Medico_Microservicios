package com.example.ms_paciente.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "validacion_documentos")
public class ValidacionDocumento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_validacion")
    private Integer idValidacion;

    @Column(name = "id_paciente", nullable = false)
    private Integer idPaciente;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "url_archivo", nullable = false)
    private String urlArchivo;

    @Column(name = "estado_validacion", nullable = false)
    private String estadoValidacion;
}