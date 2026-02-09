package com.foodDelivering.foodApp.service.UserService.impl;

import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.exception.UserNotFoundException;
import com.foodDelivering.foodApp.repository.UserRepository.UserRepository;
import com.foodDelivering.foodApp.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public @Nullable UserResponse getUserProfile(Long id) {
        return UserResponse.userTouserresponse(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found")));
    }



}
