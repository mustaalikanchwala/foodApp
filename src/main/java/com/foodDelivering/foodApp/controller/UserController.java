package com.foodDelivering.foodApp.controller;

import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.model.UserModel.User;
import com.foodDelivering.foodApp.service.UserService.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getCurrentProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getUserProfile(user.getId()));
    }

}
