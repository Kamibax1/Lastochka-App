package com.example.controller;

import com.example.model.dto.request.UserUpdateRequest;
import com.example.model.dto.response.UserResponse;
import com.example.model.dto.response.UserStatsResponse;
import com.example.service.UserService;
import com.example.service.UserStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;
    private final UserStatsService userStatsService;

    public UserController(UserService userService, UserStatsService userStatsService) {
        this.userService = userService;
        this.userStatsService = userStatsService;
    }

    @Operation(summary = "Me")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }

    @Operation(summary = "Stats user by id")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/stats/{userId}")
    public UserStatsResponse statsUser(@PathVariable("userId") UUID userId) {
        return userStatsService.getUserStats(userId);
    }

    @Operation(summary = "Update user by id")
    @SecurityRequirement(name = "bearer-jwt")
    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable("userId") UUID userId, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }
}
