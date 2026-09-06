package com.eventhub.userservice.controller;

import com.eventhub.userservice.dto.request.LoginRequest;
import com.eventhub.userservice.dto.request.RegisterRequest;
import com.eventhub.userservice.dto.response.LoginResponse;
import com.eventhub.userservice.dto.response.UserProfileResponse;
import com.eventhub.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> profile() {

        UserProfileResponse response = userService.getProfile();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {

        userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully.");

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.loginUser(request);

        return ResponseEntity.ok(response);
    }


}