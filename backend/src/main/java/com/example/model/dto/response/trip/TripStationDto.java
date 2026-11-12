package com.example.model.dto.response.trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripStationDto {
    private UUID id;
    private String code;
    private String name;
    private String city;
}
