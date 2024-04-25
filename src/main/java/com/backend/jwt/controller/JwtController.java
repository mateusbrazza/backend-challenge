package com.backend.jwt.controller;

import com.backend.jwt.response.ValidationResponse;
import com.backend.jwt.service.JwtService;
import com.backend.jwt.exception.InvalidTokenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Pattern;

/**
 * Controller for handling JWT validation requests.
 */
@RestController
@RequestMapping("/api/v1/validate")
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private JwtService jwtService;

    /**
     * Valida o JWT fornecido.
     *
     * @param authorizationHeader o cabeçalho de autorização que contém o JWT
     * @return ResponseEntity com o status da validação
     */

    @GetMapping
    public ResponseEntity<ValidationResponse> validateJwt(
            @RequestHeader("Authorization") @Pattern(regexp = "^Bearer [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$", message = "Formato de Header de autorização inválido")
            String authorizationHeader) {

        String token = authorizationHeader.substring("Bearer ".length());

        try {
            boolean isValid = jwtService.validateJwt(token);
            if (isValid) {
                logger.info("JWT válido: {}", token);
                return ResponseEntity.ok(new ValidationResponse(true, "Token válido"));
            } else {
                throw new InvalidTokenException("JWT inválido: " + token);
            }
        } catch (InvalidTokenException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ValidationResponse(false, e.getMessage()));
        }
    }
}
