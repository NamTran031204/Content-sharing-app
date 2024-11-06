package com.app.csapp.services;

import com.app.csapp.dtos.UserDTO;
import com.app.csapp.exceptions.DataNotFoundException;
import com.app.csapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
    User updateUser(long userId, UserDTO userDTO) throws DataNotFoundException;
    User getUserById(long userId);
    User updateUserPassword(long userId, UserDTO userDTO);
}