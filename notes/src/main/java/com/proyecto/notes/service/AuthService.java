package com.proyecto.notes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.notes.model.Usuario;
import com.proyecto.notes.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public String autenticar(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
    
        if (usuarioOpt.isPresent() && usuarioOpt.get().getContraseña().equals(password)) {
            Usuario usuario = usuarioOpt.get();
            String token = jwtService.generateToken(usuario.getEmail());
            return "Token: " + token;
        } else {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
    }
}