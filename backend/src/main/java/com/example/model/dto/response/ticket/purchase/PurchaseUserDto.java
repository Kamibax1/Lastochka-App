package com.example.model.dto.response.ticket.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUserDto {
    private UUID id;
    private String username;
    private String fullName;
}
