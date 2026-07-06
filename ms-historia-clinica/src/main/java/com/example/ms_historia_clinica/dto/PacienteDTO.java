package com.example.ms_historia_clinica.dto;

import lombok.Data;

@Data
public class PacienteDTO {
    private Integer idPaciente;
    private String nombres;
    private String apellidos;
    private String rut;
}