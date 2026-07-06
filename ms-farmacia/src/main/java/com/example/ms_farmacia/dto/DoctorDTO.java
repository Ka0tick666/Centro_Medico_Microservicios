package com.example.ms_farmacia.dto;

public class DoctorDTO {
    private Integer idDoctor;
    private String nombres;
    private String apellidos;
    private String especialidad;

    // --- GETTERS Y SETTERS MANUALES ---
    public Integer getIdDoctor() { return idDoctor; }
    public void setIdDoctor(Integer idDoctor) { this.idDoctor = idDoctor; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}