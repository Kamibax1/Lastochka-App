package com.example.model.dto.response.ticket.info.shorts;

import com.example.model.enums.SeatCategoryEnum;
import com.example.model.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketShortSeatDto {
    private UUID id;
    private Integer carNumber;
    private String seatNumber;
    private SeatCategoryEnum category;
    private SeatStatusEnum status;
}
