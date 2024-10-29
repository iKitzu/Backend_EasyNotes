package com.proyecto.notes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.proyecto.notes.model.Usuario;
import com.proyecto.notes.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    // Crear un nuevo usuario (sin encriptar la contraseña)
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Actualizar un usuario por ID (sin encriptar la contraseña)
    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setRol(usuarioActualizado.getRol());
            if (usuarioActualizado.getContraseña() != null) {
                usuario.setContraseña(usuarioActualizado.getContraseña());
            }
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    // Eliminar un usuario por ID
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Buscar un usuario por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // // Validar la contraseña (sin comparación encriptada)
    // public boolean validarContraseña(String contraseña, Usuario usuario) {
    //     return contraseña.equals(usuario.getContraseña());
    // }
    
    public ResponseEntity<Map<String, String>> validarUsuario(String usuario, String contrasena) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuario);

        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().getContraseña().equals(contrasena)) {
            Usuario usuarioObj = usuarioEncontrado.get();

            String token = jwtService.generateToken(usuarioObj.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Usuario o contraseña no encontrados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
