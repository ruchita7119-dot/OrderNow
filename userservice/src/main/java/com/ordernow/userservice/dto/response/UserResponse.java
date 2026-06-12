package com.ordernow.userservice.dto.response;

import com.ordernow.userservice.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;

}