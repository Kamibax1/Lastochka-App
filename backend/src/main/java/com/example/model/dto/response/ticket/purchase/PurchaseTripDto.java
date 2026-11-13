package com.example.model.dto.response.ticket.purchase;

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
public class PurchaseTripDto {
    private UUID id;
    private String routeName;
    private String originStationName;
    private String destinationStationName;
    private Instant scheduledDepTime;
    private Instant scheduledArrTime;
    private TripStatusEnum status;
}
