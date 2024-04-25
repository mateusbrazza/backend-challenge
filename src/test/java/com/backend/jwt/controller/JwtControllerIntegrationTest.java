package com.backend.jwt.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.*;

import com.backend.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JwtController.class)
public class JwtControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;


    @Test
    void validateJwt_WhenJwtIsValid_ShouldReturnVerdadeiro() throws Exception {
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNCIsIk5hZW0iOiJKYXZhSW5Vc2UifQ.0FeREzJXrNj-ZPBYKJBvCdfwAHYFDWh7GZHmFUKbGG4";;
        when(jwtService.validateJwt(validToken)).thenReturn(true);

        mockMvc.perform(get("/api/v1/validate")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .equals(true);
    }

    @Test
    void validateJwt_WhenJwtIsInvalid_ShouldReturnFalso() throws Exception {
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNCIsIk5hZW0iOiJKYXZhSW5Vc2UifQ.0FeREzJXrNj-ZPBYKJBvCdfwAHYFDWh7GZHmFUKbGG4/n";
        when(jwtService.validateJwt(invalidToken)).thenReturn(false);

        mockMvc.perform(get("/api/v1/validate")
                        .header("Authorzation", "Bearer " + invalidToken))
                .andExpect(status().isBadRequest())
                .equals(false);
    }
}
