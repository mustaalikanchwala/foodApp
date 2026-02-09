package com.foodDelivering.foodApp.service.AuthService;

import com.foodDelivering.foodApp.dto.AuthResponse;
import com.foodDelivering.foodApp.dto.LoginRequest;
import com.foodDelivering.foodApp.dto.RegisterRequest;

public interface AuthService {

    AuthResponse registerUser(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String refreshToken);
}
