
package com.ordernow.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ordernow.userservice.dto.response.UserResponse;
import com.ordernow.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/api/v1/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @Value("${internal.service.secret}")
    private String internalSecret;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable String id,
            @RequestHeader(
                value = "X-Internal-Service-Secret",
                required = false
            ) String secret) {

        // Validate internal service authentication.
        if (internalSecret == null
                || secret == null
                || !internalSecret.equals(secret)) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        return ResponseEntity.ok(userService.getUserById(id));
    }
}
