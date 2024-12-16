package com.app.csapp.services;

import com.app.csapp.components.JwtTokenUtil;
import com.app.csapp.dtos.UserDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.exceptions.SamePasswordException;
import com.app.csapp.models.Role;
import com.app.csapp.models.User;
import com.app.csapp.repositories.RoleRepository;
import com.app.csapp.repositories.UserRepository;
import com.app.csapp.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String email = userDTO.getEmail();

        // Kiểm tra bắt buộc phải có email hoặc số điện thoại
        if (email == null) {
            throw new DataIntegrityViolationException("Either phone number or email is required");
        }
        // Kiểm tra nếu email đã tồn tại
        if (email != null && userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password); // ma hoa mat khau
        User newUser = User.builder()
                .userName(userDTO.getUserName())
                .name(userDTO.getName())
                .userPassword(encodedPassword)
                .email(userDTO.getEmail())
                .profilePicture(userDTO.getProfilePicture())
                .description(userDTO.getDescription())
                .role(role)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public String login(String email, String password) throws Exception { // tra ve token
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phonenumber/password");
        }
        User existingUser = optionalUser.get();

        // tra ve token qua component JwtTokenUtil
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password,
                existingUser.getAuthorities() // lay ra quyen cua user
        );
        // authenticate with java spring
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }



    @Override
    public User getUserById(long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Page<UserResponse> getAllUser(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).map(user -> UserResponse.fromUser(user));
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(long userId, UserDTO userDTO) throws DataNotFoundException {
        User existingUser = getUserById(userId);
        if(userDTO.getUserName() != null)
            existingUser.setUserName(userDTO.getUserName());
        if(userDTO.getDescription() != null)
            existingUser.setDescription(userDTO.getDescription());

        if(userDTO.getProfilePicture() != null)
            existingUser.setProfilePicture(userDTO.getProfilePicture());
        if (userDTO.getPassword() != null) {
            if (userDTO.getPassword().equals(existingUser.getUserPassword())){
                throw new SamePasswordException("mat khau da bi trung");
            }
            String password = userDTO.getPassword();
        }

        //String encodedPassword = passwordEncoder.encode(password); // ma hoa mat khau
        userRepository.save(existingUser);
        return existingUser;
    }
}
