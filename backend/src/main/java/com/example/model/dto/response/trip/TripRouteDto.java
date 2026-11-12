package com.example.model.dto.response.trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripRouteDto {
    private UUID id;
    private String name;
    private TripStationDto originStation;
    private TripStationDto destinationStation;
    private Integer durationMin;
    private BigDecimal distanceKm;
    private BigDecimal basePrice;
    private TripTrainDto train;
}
