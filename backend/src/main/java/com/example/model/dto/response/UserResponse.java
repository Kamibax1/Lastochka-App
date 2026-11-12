package com.example.model.dto.response;

import com.example.model.enums.RoleEnum;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String avatarUrl;
    private RoleEnum role;
    private Instant createdAt;
    private Instant lastLoginAt;
}
