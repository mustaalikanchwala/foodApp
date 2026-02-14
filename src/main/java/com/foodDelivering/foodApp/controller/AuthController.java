package com.foodDelivering.foodApp.controller;

import com.foodDelivering.foodApp.dto.AuthResponse;
import com.foodDelivering.foodApp.dto.LoginRequest;
import com.foodDelivering.foodApp.dto.RegisterRequest;
import com.foodDelivering.foodApp.exception.InvalidTokenException;
import com.foodDelivering.foodApp.service.AuthService.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.registerUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authHeader){

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new InvalidTokenException("Invalid Authorization header format. Expected: Bearer <token>");
        }

        String refreshToken = authHeader.substring(7);

        // Validate token is not empty
        if (refreshToken.trim().isEmpty()) {
            throw new InvalidTokenException("Refresh token is missing");
        }

        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

}
