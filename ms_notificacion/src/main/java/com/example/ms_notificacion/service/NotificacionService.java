package com.example.ms_notificacion.service;

import com.example.ms_notificacion.model.Notificacion;
import com.example.ms_notificacion.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> listarTodas() {
        return repository.findAll();
    }

    public Notificacion guardar(Notificacion notificacion) {
        return repository.save(notificacion);
    }
}