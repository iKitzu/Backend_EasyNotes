package com.proyecto.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.proyecto.notes.model.Nota;
import com.proyecto.notes.service.NotaService;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    // Crear una nueva nota para un usuario específico
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Nota> crearNota(@PathVariable Integer usuarioId, @RequestBody Nota nota) {
        Nota nuevaNota = notaService.crearNota(usuarioId, nota);
        return nuevaNota != null ? ResponseEntity.ok(nuevaNota) : ResponseEntity.badRequest().build();
    }

    // Obtener todas las notas de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Nota>> obtenerNotasPorUsuario(@PathVariable Integer usuarioId) {
        List<Nota> notas = notaService.obtenerNotasPorUsuario(usuarioId);
        return ResponseEntity.ok(notas);
    }

    // Obtener una nota específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Nota> obtenerNotaPorId(@PathVariable Integer id) {
        Nota nota = notaService.obtenerNotaPorId(id);
        return nota != null ? ResponseEntity.ok(nota) : ResponseEntity.notFound().build();
    }

    // Actualizar una nota por ID
    @PutMapping("/{id}")
    public ResponseEntity<Nota> actualizarNota(@PathVariable Integer id, @RequestBody Nota notaActualizada) {
        Nota nota = notaService.actualizarNota(id, notaActualizada);
        return nota != null ? ResponseEntity.ok(nota) : ResponseEntity.notFound().build();
    }

    // Eliminar una nota por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNota(@PathVariable Integer id) {
        notaService.eliminarNota(id);
        return ResponseEntity.noContent().build();
    }
}
