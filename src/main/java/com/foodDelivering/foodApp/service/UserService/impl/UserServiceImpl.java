package com.foodDelivering.foodApp.service.UserService.impl;

import com.foodDelivering.foodApp.dto.UpdateProfileRequest;
import com.foodDelivering.foodApp.dto.UserResponse;
import com.foodDelivering.foodApp.exception.UpdateFieldsAreSameExcpetion;
import com.foodDelivering.foodApp.exception.UserNotFoundException;
import com.foodDelivering.foodApp.model.UserModel.User;
import com.foodDelivering.foodApp.repository.UserRepository.UserRepository;
import com.foodDelivering.foodApp.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public @Nullable UserResponse getUserProfile(Long id) {
        return UserResponse.userTouserresponse(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found")));
    }

    @Override
    @Transactional
    public UserResponse updateProfile(Long id, UpdateProfileRequest request) {
        log.info("Updating profile for id {}",id);

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found"));

        if(request.email() != null && !request.email().trim().isEmpty()){
            String newEmail = request.email().trim().toLowerCase();

            if(!newEmail.equals(user.getEmail())){
                if(userRepository.existsByEmailAndIdNot(newEmail,id)){
                    throw new UpdateFieldsAreSameExcpetion("Email already registered. Please use a different email.");
                }
            user.setEmail(newEmail);
            log.info("Updated email for user ID: {} to {}", id, newEmail);
            }
        }

        if(request.mobileNumber() != null && !request.mobileNumber().trim().isEmpty()){
            String newMobileNumber = request.mobileNumber().trim();

            if(!newMobileNumber.equals(user.getMobileNumber())){
                if(userRepository.existsByMobileNumberAndIdNot(newMobileNumber,id)){
                    throw new UpdateFieldsAreSameExcpetion("Mobile number already registered. Please use a different number.");
                }
                user.setMobileNumber(newMobileNumber);
                log.info("Updated mobile number for user ID: {}", id);
            }
        }

        if(request.username() != null && !request.username().trim().isEmpty()){
            String newUserName = request.username().trim();

            if(!newUserName.equals(user.getUsername())){
                if(userRepository.existsByUsernameAndIdNot(newUserName,id)){
                    throw new UpdateFieldsAreSameExcpetion("Username already registered. Please use a different username.");
                }

                user.setUsername(newUserName);
                log.info("Updated UserName for user ID: {} to {}", id,newUserName );
            }
        }

        if(request.fullName() != null && !request.fullName().trim().isEmpty()){
            String newFullName = request.fullName().trim();
            user.setFullName(newFullName);
            log.info("Updated full name for user ID: {}", id);
        }

        if(request.profileImage() != null && !request.profileImage().trim().isEmpty()){
            String newProfileImage = request.profileImage().trim();
            user.setProfileImage(newProfileImage);
            log.info("Updated profile image for user ID: {}", id);
        }

        return UserResponse.userTouserresponse(userRepository.save(user));
    }


}
