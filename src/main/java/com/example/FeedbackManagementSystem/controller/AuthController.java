
package com.example.FeedbackManagementSystem.controller;

import com.example.FeedbackManagementSystem.Dto.LoginRequest;
import com.example.FeedbackManagementSystem.Dto.RegisterRequest;
import com.example.FeedbackManagementSystem.Dto.UserResponse;
import com.example.FeedbackManagementSystem.Entity.User;
import com.example.FeedbackManagementSystem.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(
            @Valid @RequestBody RegisterRequest registerRequest) {

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(registerRequest.password());

        User saved = authService.register(user);

        return toResponse(saved);
    }

    @PostMapping("/login")
    public UserResponse login(
            @Valid @RequestBody LoginRequest loginRequest) {

        User user = authService.login(
                loginRequest.email(),
                loginRequest.password()
        );

        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
