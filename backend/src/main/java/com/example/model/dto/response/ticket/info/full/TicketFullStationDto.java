package com.example.model.dto.response.ticket.info.full;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFullStationDto {
    private UUID id;
    private String code;
    private String name;
    private String city;
}
