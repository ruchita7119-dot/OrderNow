package com.ordernow.userservice.mapper;

import com.ordernow.userservice.dto.response.UserResponse;
import com.ordernow.userservice.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();

    }

}