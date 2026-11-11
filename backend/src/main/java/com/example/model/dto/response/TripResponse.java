package com.example.model.dto.response;

import com.example.model.enums.TripStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
    private UUID id;
    private String routeName;
    private String trainModel;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Integer durationMinutes;
    private TripStatusEnum Status;
    private Integer availableSeatsCount;
    private Double basePrice;
}
