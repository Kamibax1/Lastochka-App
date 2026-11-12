package com.example.model.dto.response.trip;

import com.example.model.enums.TripStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
    private UUID id;
    private TripRouteDto route;
    private Instant scheduleDepTime;
    private Instant scheduleArrTime;
    private Instant actualDepTime;
    private Instant actualArrTime;
    private Integer CarCount;
    private TripStatusEnum status;
    private Integer availableSeatsCount;
}
