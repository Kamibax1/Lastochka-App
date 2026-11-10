package com.example.model.dto.response;

import com.example.model.enums.RoleEnum;
import lombok.*;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private RoleEnum role;
}
