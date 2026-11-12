package com.example.model.dto.response;

import com.example.model.enums.TripStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
    private UUID id;
    private String routeName;
    private String trainModel;
    private Instant departureTime;
    private Instant arrivalTime;
    private Integer durationMinutes;
    private TripStatusEnum Status;
    private Integer availableSeatsCount;
    private BigDecimal basePrice;
}
