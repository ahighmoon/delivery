package com.laioffer.delivery.controller;

import com.laioffer.delivery.dto.UserDto;
import com.laioffer.delivery.model.User;
import com.laioffer.delivery.repository.UserRepository;
import com.laioffer.delivery.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    private UserDto userDto;
    private User user;
    private String testUserEmail;

    @BeforeEach
    void setUp() {
        testUserEmail = "test@example.com";

        userDto = new UserDto();
        userDto.setEmail(testUserEmail);
        userDto.setPassword("password123");

        user = new User();
        user.setId("123");
        user.setEmail(testUserEmail);
        user.setPassword("encodedPassword");

        userRepository.deleteAll();
    }

    @BeforeAll
    static void beforeAll() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION", "3600000"));
    }

    @AfterAll
    static void afterAll() {
        System.clearProperty("JWT_SECRET");
        System.clearProperty("JWT_EXPIRATION");
    }


    @Test
    void testRegisterSuccess() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "test@example.com",
                  "password": "password123"
                }
                """))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Registration successful")));

        User saved = userRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(saved);
        assertNotEquals("password123", saved.getPassword());
    }

    @Test
    void testRegisterFailure() throws Exception {
        userRepository.save(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "test@example.com",
                  "password": "password123"
                }
                """))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Email already registered: " + testUserEmail));
    }

    @Test
    void testLoginSuccess() throws Exception {
        userRepository.save(user);

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(testUserEmail, "password123");
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(testUserEmail, null, Collections.emptyList());

        Mockito.when(authenticationManager.authenticate(Mockito.eq(authToken)))
              .thenReturn(authentication);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "test@example.com",
                  "password": "password123"
                }
                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    void testLoginFailure() throws Exception {
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
              .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "test@example.com",
                  "password": "wrongpassword"
                }
                """))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }

    @Test
    void testLoginFailureUserNotFound() throws Exception {
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
              .thenThrow(new BadCredentialsException("User not found"));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "email": "nonexistent@example.com",
                  "password": "password123"
                }
                """))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }
}