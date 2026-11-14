package com.example.model.dto.response.ticket.info.full;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFullUserDto {
    private UUID id;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String avatarUrl;
}
