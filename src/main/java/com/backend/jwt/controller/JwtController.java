package com.backend.jwt.controller;

import com.backend.jwt.service.JwtService;
import com.backend.jwt.exception.InvalidTokenException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "JWT Validation", description = "Endpoint for validating JWT tokens.")
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private JwtService jwtService;

    /**
     * Validates the provided JWT.
     *
     * @param authorizationHeader the authorization header containing the JWT
     * @return ResponseEntity with the status of validation (true if valid, false otherwise)
     */
    @Operation(summary = "Validates a JWT", description = "Validates a JWT provided in the authorization header.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT valid or invalid", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Bad request due to invalid format")
    })
    @GetMapping
    public ResponseEntity<Boolean> validateJwt(

            @Pattern(regexp = "^Bearer [A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$", message = "Invalid Authorization header format")
            String authorizationHeader) {

        String token = authorizationHeader.substring("Bearer ".length());
        try {
            boolean isValid = jwtService.validateJwt(token);
            logger.info("JWT validation result: {}", isValid);
            return ResponseEntity.ok(isValid);
        } catch (InvalidTokenException e) {
            logger.error("JWT validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }
}
