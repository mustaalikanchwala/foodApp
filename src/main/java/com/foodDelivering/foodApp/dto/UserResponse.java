package com.foodDelivering.foodApp.dto;

import com.foodDelivering.foodApp.model.UserModel.Role;
import com.foodDelivering.foodApp.model.UserModel.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
         Long id,
         String username,
         String fullName,
         String email,
         String mobileNumber,
         Role role,
         String profileImage,
         Boolean isActive,
         LocalDateTime createdAt,
         LocalDateTime updatedAt

) {
    public static UserResponse userTouserresponse(User user){
        return UserResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsernameField())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole())
                .profileImage(user.getProfileImage())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
