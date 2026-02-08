package com.foodDelivering.foodApp.dto;

public record AuthResponse(
         String accessToken,
         String refreshToken,
         String tokenType,
         Long expiresIn,
         UserResponse user
) {
}
