package com.tarea01ferle.series.service;

import com.tarea01ferle.series.models.Serie;
import com.tarea01ferle.series.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<Serie> obtenerTodas() {
        return serieRepository.findAll();
    }

    public Optional<Serie> obtenerPorId(Long id) {
        return serieRepository.findById(id);
    }

    public Serie guardarSerie(Serie serie) {
        return serieRepository.save(serie);
    }

    public Optional<Serie> actualizarSerie(Long id, Serie serieDetalles) {
        return serieRepository.findById(id).map(serieExistente -> {
            serieExistente.setTitulo(serieDetalles.getTitulo());
            serieExistente.setPlataforma(serieDetalles.getPlataforma());
            serieExistente.setCalificacion(serieDetalles.getCalificacion());
            return serieRepository.save(serieExistente);
        });
    }

    public boolean eliminarSerie(Long id) {
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
            return true;
        }
        return false;
    }
}