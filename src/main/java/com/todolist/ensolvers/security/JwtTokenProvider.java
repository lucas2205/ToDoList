package com.todolist.ensolvers.security;


import com.todolist.ensolvers.model.User;
import com.todolist.ensolvers.repository.IUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    @Autowired
    IUserRepository userRepository;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        System.out.println("username ----->"+ username);
        User user = userRepository.findByEmail(username).get();

        Date fechaActual = new Date();
        Date fechaExpiration = new Date(fechaActual.getTime() + jwtExpiration);
        System.out.println("ID ------->"+ Long.toString(user.getId()));

        return Jwts.builder().setSubject(username).setId(Long.toString(user.getId())).setIssuedAt(new Date()).setExpiration(fechaExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    }

    public String getUsernameToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public Long getUserId(String token) {

        String jwt =token.substring(7);

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody();

        return Long.valueOf(claims.getId());
    }

    public boolean validarToken(String token) {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
    }

}