package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Genera una clave segura para firmar el token
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Método para generar el token
    public String generateToken(Usuario usuario) {
        // Extraer el primer rol del usuario o establecer un valor por defecto
        String rol = usuario.getRoles().stream()
                .findFirst()
                .map(role -> role.getNombre()) // Suponiendo que `Rol` tiene un campo `nombre`
                .orElse("ROLE_USER"); // Rol por defecto en caso de que no tenga ningún rol asignado

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .claim("usuarioId", usuario.getId()) // Agrega el usuarioId al token
                .claim("rol", rol) // Agrega el rol al token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validez
                .signWith(SECRET_KEY) // Usando Key en lugar de String
                .compact();
    }

    // Validar el token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Obtener el nombre de usuario desde el token
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer el nombre de usuario o cualquier otro dato del token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Método para extraer el rol del token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("rol", String.class));
    }

    // Método genérico para extraer cualquier claim del token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtener todos los claims del token JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para extraer el ID de usuario
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return Long.valueOf(claims.get("usuarioId").toString());
    }
}
