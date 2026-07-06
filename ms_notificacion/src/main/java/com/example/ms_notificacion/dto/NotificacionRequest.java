package com.example.ms_notificacion.dto;

public class NotificacionRequest {
    private String emailDestinatario;
    private String mensaje;

    // 1. Constructor vacío 
    public NotificacionRequest() {
    }

    // 2. Constructor con parámetros
    public NotificacionRequest(String emailDestinatario, String mensaje) {
        this.emailDestinatario = emailDestinatario;
        this.mensaje = mensaje;
    }

    // 3. Getters y Setters
    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}