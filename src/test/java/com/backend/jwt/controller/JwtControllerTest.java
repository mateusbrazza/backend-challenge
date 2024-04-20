package com.backend.jwt.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.backend.jwt.controller.JwtController;
import com.backend.jwt.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(JwtController.class)
public class JwtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Test
    void quandoHeaderDeAutorizacaoInvalido_entaoBadRequest() throws Exception {
        mockMvc.perform(get("/validate")
                        .header("Authorization", "Invalid token"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Formato de Header de autorização inválido"));
    }

    @Test
    void quandoTokenEhValido_entaoRespondeVerdadeiro() throws Exception {
        String validToken = "Bearer validtoken123";
        when(jwtService.validateJwt("validtoken123")).thenReturn(true);

        mockMvc.perform(get("/validate")
                        .header("Authorization", validToken))
                .andExpect(status().isOk())
                .andExpect(content().string("verdadeiro"));
    }

    @Test
    void quandoTokenEhInvalido_entaoRespondeFalso() throws Exception {
        String invalidToken = "Bearer invalidtoken123";
        when(jwtService.validateJwt("invalidtoken123")).thenReturn(false);

        mockMvc.perform(get("/validate")
                        .header("Authorization", invalidToken))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("falso"));
    }
}
