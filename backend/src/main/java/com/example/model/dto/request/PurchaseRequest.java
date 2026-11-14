package com.example.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class PurchaseRequest {
    @NotNull
    @JsonProperty("trip_id")
    private UUID tripId;

    @NotNull
    @JsonProperty("seat_id")
    private UUID seatId;

    @NotNull
    @JsonProperty("user_id")
    private UUID userId;
}
