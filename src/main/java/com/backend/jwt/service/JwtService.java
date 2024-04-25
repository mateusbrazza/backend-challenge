package com.backend.jwt.service;

import com.backend.jwt.util.RoleValidator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final int NAME_MAX_LENGTH = 256;

    private final SecretKey secretKey;
    private final RoleValidator roleValidator;

    public JwtService(@Value("${jwt.secretKey}") String base64SecretKey, RoleValidator roleValidator) {
        this.roleValidator = roleValidator;
        byte[] decodedKey = Base64.getDecoder().decode(base64SecretKey);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, HMAC_SHA256);
    }

    public boolean validateJwt(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            logger.error("O token JWT é nulo ou vazio.");
            return false;
        }

        try {
            Jws<Claims> claims = parseClaims(jwt);
            Claims body = claims.getBody();

            if (!isValidName(body.get("Name", String.class))) {
                logger.error("Falha na validação do JWT: Nome inválido.");
                return false;
            }

            if (!isValidSeed(body.get("Seed", String.class))) {
                logger.error("Falha na validação do JWT: Seed inválido.");
                return false;
            }

            if (!roleValidator.isValidRole(body.get("Role", String.class))) {
                logger.error("Falha na validação do JWT: Role inválido.");
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Erro ao validar o token JWT: {}", e.getMessage());
            return false;
        }
    }

    private Jws<Claims> parseClaims(String jwt) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return parser.parseClaimsJws(jwt);
    }

    private boolean isValidName(String name) {
        return name != null && !name.matches(".*\\d.*") && name.length() <= NAME_MAX_LENGTH;
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
