package com.example.ms_seguro.service;

import com.example.ms_seguro.model.Seguro;
import com.example.ms_seguro.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository repository;

    public List<Seguro> obtenerTodos() {
        return repository.findAll();
    }

    public Seguro guardarSeguro(Seguro seguro) {
        if (seguro.getCoberturas() != null) {
            seguro.getCoberturas().forEach(cobertura -> cobertura.setSeguro(seguro));
        }
        return repository.save(seguro);
    }

    public Seguro buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}