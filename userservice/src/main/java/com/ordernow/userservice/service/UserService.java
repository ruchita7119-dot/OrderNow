package com.ordernow.userservice.service;

import java.util.List;

import com.ordernow.userservice.dto.request.LoginRequest;
import com.ordernow.userservice.dto.request.RegisterRequest;
import com.ordernow.userservice.dto.request.UpdateUserRequest;
import com.ordernow.userservice.dto.response.AuthResponse;
import com.ordernow.userservice.dto.response.UserResponse;
public interface UserService {

    UserResponse registerUser(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getUserById(String id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(String id, UpdateUserRequest request);

    void deleteUser(String id);

}