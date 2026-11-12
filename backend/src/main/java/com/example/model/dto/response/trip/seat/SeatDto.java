package com.example.model.dto.response.trip.seat;

import com.example.model.enums.SeatCategoryEnum;
import com.example.model.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private UUID id;
    private String seatNumber;
    private SeatCategoryEnum category;
    private SeatStatusEnum status;
    private BigDecimal price;
}
