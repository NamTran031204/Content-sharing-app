package com.app.csapp.services;

import com.app.csapp.dtos.UserDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.User;
import com.app.csapp.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String email, String password) throws Exception;
    User updateUser(long userId, UserDTO userDTO) throws DataNotFoundException;
    void deleteUser(long id);

    User getUserById(long userId);
    Page<UserResponse> getAllUser(PageRequest pageRequest);

}