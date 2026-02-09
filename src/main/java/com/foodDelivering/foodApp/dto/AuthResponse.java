package com.foodDelivering.foodApp.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
         String accessToken,
         String refreshToken,
         String tokenType,
         Long expiresIn,
         UserResponse user
) {

    public static AuthResponse userResponsetoAuthResponse(UserResponse user , String accessToken , String refreshToken, Long expiration){
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .expiresIn(expiration)
                .tokenType("Bearer")
                .build();
    }

}
