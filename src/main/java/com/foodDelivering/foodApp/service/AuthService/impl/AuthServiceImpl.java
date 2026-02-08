package com.foodDelivering.foodApp.service.AuthService.impl;

import com.foodDelivering.foodApp.dto.AuthResponse;
import com.foodDelivering.foodApp.dto.RegisterRequest;
import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.exception.UserAlreadyExistsException;
import com.foodDelivering.foodApp.model.UserModel.User;
import com.foodDelivering.foodApp.repository.UserRepository.UserRepository;
import com.foodDelivering.foodApp.service.AuthService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerUser( RegisterRequest request) {

        if(userRepository.existsByUsername(request.username())){
            throw new UserAlreadyExistsException("Username Already Exists");
        }
        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException("Email Already Exists");
        }
        if(userRepository.existsByMobileNumber(request.mobileNumber())){
            throw new UserAlreadyExistsException("Mobile Number Already Exists");
        }

        String hashPassword = passwordEncoder.encode(request.password());

        User requestUser = User.requestTouser(request,hashPassword);

        UserResponse response = UserResponse.userTouserresponse(userRepository.save(requestUser));

        return null;
    }
}
