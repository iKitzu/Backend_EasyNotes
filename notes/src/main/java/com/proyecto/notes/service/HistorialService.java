package com.proyecto.notes.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.notes.model.Historial;
import com.proyecto.notes.model.Nota;
import com.proyecto.notes.repository.HistorialRepository;
import com.proyecto.notes.repository.NotaRepository;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private NotaRepository notaRepository;

    public Historial crearHistorial(Integer notaId, String contenidoAnterior) {
        Nota nota = notaRepository.findById(notaId).orElse(null);
        if (nota != null) {
            Historial historial = new Historial();
            historial.setNota(nota);
            historial.setContenidoAnterior(contenidoAnterior);
            return historialRepository.save(historial);
        }
        return null;
    }

    public List<Historial> obtenerHistorialPorNota(Integer notaId) {
        return historialRepository.findByNotaId(notaId);
    }
}
