package com.foodDelivering.foodApp.controller;

import com.foodDelivering.foodApp.dto.AuthResponse;
import com.foodDelivering.foodApp.dto.LoginRequest;
import com.foodDelivering.foodApp.dto.RegisterRequest;
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
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

}
