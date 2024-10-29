package com.proyecto.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.notes.model.Historial;
import com.proyecto.notes.service.HistorialService;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    // Crear una entrada en el historial para una nota específica
    @PostMapping("/nota/{notaId}")
    public ResponseEntity<Historial> crearHistorial(@PathVariable Integer notaId, @RequestBody String contenidoAnterior) {
        Historial nuevoHistorial = historialService.crearHistorial(notaId, contenidoAnterior);
        return nuevoHistorial != null ? ResponseEntity.ok(nuevoHistorial) : ResponseEntity.badRequest().build();
    }

    // Obtener el historial de una nota específica
    @GetMapping("/nota/{notaId}")
    public ResponseEntity<List<Historial>> obtenerHistorialPorNota(@PathVariable Integer notaId) {
        List<Historial> historial = historialService.obtenerHistorialPorNota(notaId);
        return ResponseEntity.ok(historial);
    }
}
