package com.foodDelivering.foodApp.service.AuthService.impl;

import com.foodDelivering.foodApp.dto.AuthResponse;
import com.foodDelivering.foodApp.dto.LoginRequest;
import com.foodDelivering.foodApp.dto.RegisterRequest;
import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.exception.InvalidCredentialsException;
import com.foodDelivering.foodApp.exception.UserAccontDeActivateException;
import com.foodDelivering.foodApp.exception.UserAlreadyExistsException;
import com.foodDelivering.foodApp.exception.UserNotFoundException;
import com.foodDelivering.foodApp.model.UserModel.User;
import com.foodDelivering.foodApp.repository.UserRepository.UserRepository;
import com.foodDelivering.foodApp.security.JwtUtil;
import com.foodDelivering.foodApp.service.AuthService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

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

        User savedUser = userRepository.save(requestUser);

        UserResponse response = UserResponse.userTouserresponse(savedUser);

        String accessToken = jwtUtil.genrerateAccessToken(savedUser.getId(), savedUser.getRole().name(),savedUser.getEmail(), savedUser.getUsername(), savedUser.getFullName());

        String refreshToken = jwtUtil.genrerateRefreshToken(savedUser.getId(),savedUser.getEmail());

        return AuthResponse.userResponsetoAuthResponse(response,accessToken,refreshToken, jwtUtil.getExpirationInSeconds());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        }catch (BadCredentialsException e){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = (User) userRepository.findByEmail(request.email()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getIsActive()){
            throw new UserAccontDeActivateException("Account is deactivated. Please contact support.");
        }

        UserResponse response = UserResponse.userTouserresponse(user);

        String accessToken = jwtUtil.genrerateAccessToken(user.getId(), user.getRole().name(),user.getEmail(), user.getUsername(), user.getFullName());

        String refreshToken = jwtUtil.genrerateRefreshToken(user.getId(),user.getEmail());

        return AuthResponse.userResponsetoAuthResponse(response, accessToken,refreshToken, jwtUtil.getExpirationInSeconds() );
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        return null;
    }
}
