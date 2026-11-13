package com.example.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class PurchaseRequest {
    private UUID tripId;
    private UUID seatId;
    private UUID userId;
}
