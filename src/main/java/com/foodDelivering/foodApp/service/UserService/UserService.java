package com.foodDelivering.foodApp.service.UserService;

import com.foodDelivering.foodApp.dto.ChangePasswordRequest;
import com.foodDelivering.foodApp.dto.UpdateProfileRequest;
import com.foodDelivering.foodApp.dto.UserResponse;
import jakarta.validation.Valid;

public interface UserService {

    UserResponse getUserProfile(Long userid);

    UserResponse updateProfile(Long id, @Valid UpdateProfileRequest request);

    boolean changePassword(Long id, @Valid ChangePasswordRequest request);
}
