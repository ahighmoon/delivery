package com.laioffer.delivery.controller;

import com.laioffer.delivery.dto.UserDto;
import com.laioffer.delivery.model.User;
import com.laioffer.delivery.service.UserService;
import com.laioffer.delivery.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto dto) {
        try {
            User newUser = userService.createUser(dto);
            return ResponseEntity.ok("Registration successful, userId: " + newUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto dto) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        try {
            Authentication authResult = authenticationManager.authenticate(authToken);
            String email = authResult.getName();
            String token = JwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login successful"
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // example
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // TODO: Implement the actual logic
        return ResponseEntity.ok(List.of());
    }

}
