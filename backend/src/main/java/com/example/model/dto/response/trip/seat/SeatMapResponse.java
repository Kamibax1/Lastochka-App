package com.example.model.dto.response.trip.seat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatMapResponse {
    private UUID tripId;
    private Integer carCount;
    private List<CarDto> cars;
}
