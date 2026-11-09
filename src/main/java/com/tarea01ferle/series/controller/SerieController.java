package com.tarea01ferle.series.controller;

import com.tarea01ferle.series.models.Serie;
import com.tarea01ferle.series.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public ResponseEntity<List<Serie>> listarSeries() {
        return ResponseEntity.ok(serieService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> obtenerSeriePorId(@PathVariable Long id) {
        return serieService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearSerie(@RequestBody Serie serie) {
        if (serie.getTitulo() == null || serie.getTitulo().trim().isEmpty() ||
                serie.getCalificacion() == null || serie.getCalificacion() < 1.0 || serie.getCalificacion() > 10.0) {
            return ResponseEntity.badRequest()
                    .body("Datos inválidos: El título es obligatorio y la calificación debe estar entre 1 y 10.");
        }
        Serie nuevaSerie = serieService.guardarSerie(serie);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSerie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSerie(@PathVariable Long id, @RequestBody Serie serieDetalles) {
        if (serieDetalles.getCalificacion() != null &&
                (serieDetalles.getCalificacion() < 1.0 || serieDetalles.getCalificacion() > 10.0)) {
            return ResponseEntity.badRequest()
                    .body("Datos inválidos: La calificación debe estar entre 1 y 10.");
        }
        return serieService.actualizarSerie(id, serieDetalles)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSerie(@PathVariable Long id) {
        if (serieService.eliminarSerie(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}