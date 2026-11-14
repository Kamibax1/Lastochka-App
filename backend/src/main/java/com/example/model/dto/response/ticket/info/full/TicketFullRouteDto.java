package com.example.model.dto.response.ticket.info.full;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFullRouteDto {
    private UUID id;
    private String name;
    private TicketFullStationDto originStation;
    private TicketFullStationDto destinationStation;
    private Integer durationMin;
    private BigDecimal distanceKm;
    private TicketFullTrainDto train;
}
