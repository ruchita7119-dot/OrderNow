package com.ordernow.userservice.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ordernow.userservice.dto.request.LoginRequest;
import com.ordernow.userservice.dto.request.RegisterRequest;
import com.ordernow.userservice.dto.request.UpdateUserRequest;
import com.ordernow.userservice.dto.response.AuthResponse;
import com.ordernow.userservice.dto.response.UserResponse;
import com.ordernow.userservice.entity.Role;
import com.ordernow.userservice.entity.User;
import com.ordernow.userservice.exception.EmailAlreadyExistsException;
import com.ordernow.userservice.exception.ResourceNotFoundException;
import com.ordernow.userservice.mapper.UserMapper;
import com.ordernow.userservice.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.ordernow.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    @Override
    public UserResponse registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(Role.USER)
                .enabled(true)
                .emailVerified(false)
                .build();

        User savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    
    @Override
    public UserResponse getUserById(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());

        User updatedUser = userRepository.save(user);

        return UserMapper.toUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        userRepository.delete(user);
    }


    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build();

        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(UserMapper.toUserResponse(user))
                .build();
    }
}