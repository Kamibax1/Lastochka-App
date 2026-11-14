package com.example.model.dto.response.ticket.info.full;

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
public class TicketFullTripDto {
    private UUID id;
    private TicketFullRouteDto route;
    private Instant scheduleDepTime;
    private Instant scheduleArrTime;
    private Instant actualDepTime;
    private Instant actualArrTime;
    private Integer carCount;
    private TripStatusEnum status;
}
