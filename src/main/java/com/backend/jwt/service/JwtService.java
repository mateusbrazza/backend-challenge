package com.backend.jwt.service;


import com.backend.jwt.util.RoleValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RoleValidator roleValidator;

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

            return validateClaims(claims.getBody());
        } catch (Exception e) {
            logger.error("Erro ao validar o token JWT: {}", e.getMessage());
            return false;
        }
    }

    private boolean validateClaims(Claims claims) {
        boolean validName = isValidName(claims.get("Name", String.class));
        boolean validRole = roleValidator.isValidRole(claims.get("Role", String.class));
        boolean validSeed = isValidSeed(claims.get("Seed", String.class));
        logger.info("valida claims - Name: {}, Role: {}, Seed: {}", validName, validRole, validSeed);
        return validName && validRole && validSeed && claims.size() == 3;
    }

    private boolean isValidName(String name) {
        return name != null && !name.matches(".*\\d.*") && name.length() <= 256;
    }

    private boolean isValidSeed(String seed) {
        try {
            int num = Integer.parseInt(seed);
            return isPrime(num);
        } catch (NumberFormatException e) {
            logger.error("Formato inicial inválido: {}", e.getMessage());
            return false;
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;
    }



}