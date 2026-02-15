package com.foodDelivering.foodApp.controller;

import com.foodDelivering.foodApp.dto.ChangePasswordRequest;
import com.foodDelivering.foodApp.dto.UpdateProfileRequest;
import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.model.UserModel.User;
import com.foodDelivering.foodApp.service.UserService.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getCurrentProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getUserProfile(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateProfileRequest request) {

        UserResponse response = userService.updateProfile(user.getId(), request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String,String>> changePass(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePasswordRequest request) {

            boolean isChange = userService.changePassword(user.getId(),request);

            if(isChange){
                return ResponseEntity.ok(Map.of(
                        "message","Password Change Successfully"
                ));
            }else{
                return ResponseEntity.ok(Map.of(
                        "message","Failed To Change Password"
                ));
            }
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<Map<String, String>> deactivateAccount(@AuthenticationPrincipal User user) {
        boolean isDeactive = userService.deactivateAccount(user.getId());
        if(isDeactive){
            return ResponseEntity.ok(Map.of(
                    "message", "Account deactivated successfully. You can no longer login."
            ));
        }else{
            return ResponseEntity.ok(Map.of(
                    "message", "Account Deactivation Fail. Plz try again."
            ));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String,Object>> getUserStatus(@AuthenticationPrincipal User user){
        boolean isActive = userService.isUserActive(user.getId());
        return ResponseEntity.ok(Map.of(
                "userId",user.getId(),
                "email",user.getEmail(),
                "isActive",user.getIsActive()
        ));
    }


}
