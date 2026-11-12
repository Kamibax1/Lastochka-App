package com.example.model.dto.response.trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripTrainDto {
    private UUID id;
    private String model;
    private Integer capacity;
    private Boolean wifi;
    private Integer powerOutlets;
    private Boolean bikeSpace;
}
