package com.test.projeto_full.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenProvider implements Serializable {

    @Value("${jwt.secret:mySecretKeyForJWT}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}") // 1 hora em milissegundos
    private long jwtExpirationInMs;

    /**
     * Gera um JWT token a partir do username
     * @param username - nome do usuário autenticado
     * @return JWT token
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Gera um JWT token a partir de um objeto Authentication
     * @param authentication - autenticação do Spring Security
     * @return JWT token
     */
    public String generateTokenFromAuth(Authentication authentication) {
        return generateToken(authentication.getName());
    }

    /**
     * Extrai o username do JWT token
     * @param token - JWT token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Valida o JWT token
     * @param token - JWT token
     * @return true se o token é válido
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrai o JWT token do header Authorization
     * @param authorizationHeader - valor do header Authorization
     * @return JWT token ou null se não encontrado
     */
    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}

