package com.example.ms_metricacentro.service;

import com.example.ms_metricacentro.model.Metricacentro;
import com.example.ms_metricacentro.repository.MetricacentroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MetricacentroService {

    @Autowired
    private MetricacentroRepository repository;

    public List<Metricacentro> listarTodas() {
        return repository.findAll();
    }

    public Metricacentro guardar(Metricacentro metricacentro) {
        return repository.save(metricacentro);
    }
}