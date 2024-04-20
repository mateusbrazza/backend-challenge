package com.backend.jwt.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secretKey}")
    private String base64SecretKey;

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getUrlDecoder().decode(base64SecretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public boolean validateJwt(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            logger.error("O token JWT é nulo ou vazio ");
            return false;
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwt);

            return true;
        } catch (Exception e) {
            logger.error("Erro ao validar o token JWT: {}", e.getMessage());
            return false;
        }
    }

}