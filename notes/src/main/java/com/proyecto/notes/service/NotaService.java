package com.proyecto.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.notes.model.Nota;
import com.proyecto.notes.model.Usuario;
import com.proyecto.notes.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Nota crearNota(Integer usuarioId, Nota nota) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usuario != null) {
            nota.setUsuario(usuario);
            return notaRepository.save(nota);
        }
        return null;
    }

    public Nota obtenerNotaPorId(Integer id) {
        return notaRepository.findById(id).orElse(null);
    }

    public List<Nota> obtenerNotasPorUsuario(Integer usuarioId) {
        return notaRepository.findByUsuarioId(usuarioId);
    }

    public Nota actualizarNota(Integer id, Nota notaActualizada) {
        return notaRepository.findById(id).map(nota -> {
            nota.setTitulo(notaActualizada.getTitulo());
            nota.setContenido(notaActualizada.getContenido());
            return notaRepository.save(nota);
        }).orElse(null);
    }

    public void eliminarNota(Integer id) {
        notaRepository.deleteById(id);
    }
}
