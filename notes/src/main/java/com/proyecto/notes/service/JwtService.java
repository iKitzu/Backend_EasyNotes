package com.proyecto.notes.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

    private final String SECRET_KEY = "miClaveSecreta"; // Usa una clave secreta para firmar el JWT (asegurate de mantenerla segura)

    public String generateToken(String email) {

        Date expirationTime = new Date(System.currentTimeMillis() + 600000);

        //Generar el token JWT
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    
}