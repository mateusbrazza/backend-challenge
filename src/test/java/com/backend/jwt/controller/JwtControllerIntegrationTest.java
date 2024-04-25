package com.backend.jwt.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import com.backend.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    public void validateJwt_WhenJwtIsValid_ShouldReturnValidResponse() throws Exception {
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNCIsIk5hZW0iOiJKYXZhSW5Vc2UifQ.0FeREzJXrNj-ZPBYKJBvCdfwAHYFDWh7GZHmFUKbGG4";
        when(jwtService.validateJwt(validToken)).thenReturn(true);

        mockMvc.perform(get("/api/v1/validate")
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", is(true)))
                .andExpect(jsonPath("$.message", is("Token válido")));
    }

    @Test
    public void validateJwt_WhenJwtIsInvalid_ShouldReturnErrorResponse() throws Exception {
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNCIsIk5hZW0iOiJKYXZhSW5Vc2UifQ.0FeREzJXrNj-ZPBYKJBvCdfwAHYFDWh7GZHmFUKbGG4";
        when(jwtService.validateJwt(invalidToken)).thenReturn(false);

        mockMvc.perform(get("/api/v1/validate")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.valid", is(false)))
                .andExpect(jsonPath("$.message", startsWith("JWT inválido")));
    }
}
