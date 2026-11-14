package com.example.model.dto.response.ticket.info.full;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFullTrainDto {
    private UUID id;
    private String model;
    private Boolean wifi;
    private Integer powerOutlets;
    private Boolean bikeSpace;
}
