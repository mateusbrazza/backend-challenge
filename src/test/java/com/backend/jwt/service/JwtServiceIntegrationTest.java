package com.backend.jwt.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JwtServiceIntegrationTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testValidateJwt() {
        String validJwt = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiMyIsIk5hbWUiOiJNYXRldXMgSk9hcXVpbSJ9.0aUeoBLfIUN9E0fyZvgVNxQ1qJi_kc8F3XNyWMrFeK4";
        String invalidJwt = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNCIsIk5hZW0iOiJKYXZhSW5Vc2UifQ.0FeREzJXrNj-ZPBYKJBvCdfwAHYFDWh7GZHmFUKbGG4\n";

        assertTrue(jwtService.validateJwt(validJwt));
        assertFalse(jwtService.validateJwt(invalidJwt));
    }
}