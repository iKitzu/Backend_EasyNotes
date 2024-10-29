package com.proyecto.notes.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "miClaveSecreta";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
    
        // Permitir solicitudes al endpoint de login y usuarios
        if (requestURI.equals("/auth/login") || requestURI.startsWith("/api/usuarios/")) {
            chain.doFilter(request, response);
            return; // No procesar más si es uno de estos endpoints
        }
    
        String authorizationHeader = request.getHeader("Authorization");
    
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
    
                String email = decodedJWT.getSubject(); // Extraer el email del token
    
                if (email != null) {
                    // Crear un objeto de autenticación basado en el email extraído
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
    
            } catch (JWTVerificationException e) {
                // Token no válido y se rechaza la solicitud
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
    
        chain.doFilter(request, response);
    }    
}
