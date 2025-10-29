package com.tekmez.crud.controller;

import com.tekmez.crud.model.entity.User;
import com.tekmez.crud.enums.Role;
import com.tekmez.crud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@example.com");
        user.setPasswordHash("encoded-password");
        user.setRole(Role.USER);

        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("12345678", "encoded-password"))
                .thenReturn(true);
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsValid() throws Exception {
        String json = """
            { "email": "test@example.com", "password": "123456" }
        """;

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
