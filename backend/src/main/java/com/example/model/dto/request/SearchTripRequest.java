package com.example.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
public class SearchTripRequest {
    private UUID originId;
    private UUID destinationId;
    private LocalDate date;
}
