package com.app.csapp.services;

import com.app.csapp.dtos.UserDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.exceptions.SamePasswordException;
import com.app.csapp.models.User;
import com.app.csapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        String email = userDTO.getEmail();

        // Kiểm tra bắt buộc phải có email hoặc số điện thoại
        if (phoneNumber == null && email == null) {
            throw new DataIntegrityViolationException("Either phone number or email is required");
        }

        // Kiểm tra nếu số điện thoại đã tồn tại
        if (phoneNumber != null && userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Kiểm tra nếu email đã tồn tại
        if (email != null && userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        User newUser = User.builder()
                .userName(userDTO.getUserName())
                .userPassword(userDTO.getPassword())
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .profilePicture(userDTO.getProfilePicture())
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return "";
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUserPassword(long userId, UserDTO userDTO) {
        User existingUser = getUserById(userId);
        if (userDTO.getPassword().equals(existingUser.getUserPassword())){
            throw new SamePasswordException("mat khau da bi trung");
        }
        existingUser.setUserPassword(userDTO.getPassword());
        userRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public User updateUser(long userId, UserDTO userDTO) throws DataNotFoundException {
        User existingUser = getUserById(userId);
        if(userDTO.getUserName() != null)
            existingUser.setUserName(userDTO.getUserName());
//        if(userDTO.getUserDescription() != null)
//            existingUser.setUserDescription(userDTO.getUserDescription());

        if(userDTO.getProfilePicture() != null)
            existingUser.setProfilePicture(userDTO.getProfilePicture());
        userRepository.save(existingUser);
        return existingUser;
    }
}
