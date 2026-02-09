package com.foodDelivering.foodApp.service.AuthService.impl;

import com.foodDelivering.foodApp.dto.AuthResponse;
import com.foodDelivering.foodApp.dto.LoginRequest;
import com.foodDelivering.foodApp.dto.RegisterRequest;
import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.exception.*;
import com.foodDelivering.foodApp.model.UserModel.User;
import com.foodDelivering.foodApp.repository.UserRepository.UserRepository;
import com.foodDelivering.foodApp.security.JwtUtil;
import com.foodDelivering.foodApp.service.AuthService.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public AuthResponse refreshToken(String refreshToken) {
        try{
           if(jwtUtil.isTokenExpired(refreshToken)){
               throw new InvalidTokenException("Refresh token has expired. Please login again.");
           }

           Long userId = jwtUtil.extractUserId(refreshToken);
           String email = jwtUtil.extractEmail(refreshToken);

           User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

           if(!user.getEmail().equals(email)){
               throw new InvalidTokenException("Invalid refresh token");
           }

           if(!user.getIsActive()){
               throw new UserAccontDeActivateException("Account is deactivated. Please contact support.");
           }

           String accessToken = jwtUtil.genrerateAccessToken(user.getId(),user.getRole().name(),user.getEmail(), user.getUsernameField(), user.getFullName());

           UserResponse response = UserResponse.userTouserresponse(user);

           return AuthResponse.userResponsetoAuthResponse(response, accessToken,refreshToken, jwtUtil.getExpirationInSeconds() );

        }catch (ExpiredJwtException ex) {
            log.error("Refresh token expired: {}", ex.getMessage());
            throw new InvalidTokenException("Refresh token has expired. Please login again.");
        } catch (JwtException ex) {
            log.error("Invalid refresh token: {}", ex.getMessage());
            throw new InvalidTokenException("Invalid refresh token. Please login again.");
        } catch (Exception ex) {
            log.error("Error refreshing token", ex);
            throw new RuntimeException("Failed to refresh token: " + ex.getMessage());
        }
    }
}
