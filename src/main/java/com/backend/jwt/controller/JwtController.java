package com.backend.jwt.controller;

import com.backend.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/validate")
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<String> validateJwt(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.error("Formato de Header de autorização inválido");
            return ResponseEntity.badRequest().body("Formato de Header de autorização inválido");
        }

        String token = authorizationHeader.substring("Bearer ".length());

        boolean isValid = jwtService.validateJwt(token);
        if (isValid) {
            logger.info("JWT valido", token);
            return ResponseEntity.ok("verdadeiro");
        } else {
            logger.error("JWT invalido: {}", token);
            return ResponseEntity.badRequest().body("falso");
        }
    }
}