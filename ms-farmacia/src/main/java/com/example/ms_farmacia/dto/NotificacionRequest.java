package com.example.ms_farmacia.dto;

public class NotificacionRequest {
    private String emailDestinatario;
    private String mensaje;

    public NotificacionRequest() {}

    public NotificacionRequest(String emailDestinatario, String mensaje) {
        this.emailDestinatario = emailDestinatario;
        this.mensaje = mensaje;
    }

    public String getEmailDestinatario() { return emailDestinatario; }
    public void setEmailDestinatario(String emailDestinatario) { this.emailDestinatario = emailDestinatario; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}