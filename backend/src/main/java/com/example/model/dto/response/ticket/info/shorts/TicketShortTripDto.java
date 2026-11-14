package com.example.model.dto.response.ticket.info.shorts;

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
public class TicketShortTripDto {
    private UUID id;
    private String routeName;
    private String originStationName;
    private String destinationStationName;
    private Instant scheduleDepTime;
    private Instant scheduleArrTime;
    private TripStatusEnum status;
}
