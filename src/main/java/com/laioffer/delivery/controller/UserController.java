package com.laioffer.delivery.controller;

import com.laioffer.delivery.model.User;
import com.laioffer.delivery.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // example
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // TODO: Implement the actual logic
        return ResponseEntity.ok(List.of());
    }

}
