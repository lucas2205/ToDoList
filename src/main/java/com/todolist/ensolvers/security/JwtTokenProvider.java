package com.todolist.ensolvers.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date fechaActual = new Date();
        Date fechaExpiration = new Date(fechaActual.getTime() + jwtExpiration);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        return token;

    }

    public String obtenerUsernameDelJwt(String token) {

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token) {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
    }

}