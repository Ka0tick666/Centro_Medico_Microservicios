package com.example.ms_historia_clinica.dto;

import lombok.Data;

@Data
public class RecetaDTO {
    private Integer idReceta;
    private Integer idMedico;
    private String estado;
}