package com.example.model.dto.response.trip.seat;

import com.example.model.enums.SeatCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Integer carNumber;
    private List<SeatCategoryEnum> categories;
    private List<SeatDto> seats;
}
