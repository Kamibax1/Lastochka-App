package com.example.model.mapper;

import com.example.model.dto.response.trip.seat.CarDto;
import com.example.model.dto.response.trip.seat.SeatDto;
import com.example.model.dto.response.trip.seat.SeatMapResponse;
import com.example.model.entity.SeatEntity;
import com.example.model.entity.TripEntity;
import com.example.model.enums.SeatCategoryEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class SeatMapMapper {
    public SeatMapResponse toSeatMapResponse(TripEntity tripEntity) {
        Map<Integer, List<SeatEntity>> seatsByCar = tripEntity.getSeats().stream()
                .collect(Collectors.groupingBy(
                        SeatEntity::getCarNumber,
                        TreeMap::new,
                        Collectors.toList()
                ));

        List<CarDto> cars = seatsByCar.entrySet().stream()
                .map(entry -> toCarDto(entry.getKey(), entry.getValue(), tripEntity))
                .toList();

        SeatMapResponse response = new SeatMapResponse();
        response.setTripId(tripEntity.getId());
        response.setCarCount(cars.size());
        response.setCars(cars);
        return response;
    }

    private SeatDto toSeatDto(SeatEntity seatEntity, TripEntity tripEntity) {
        BigDecimal basePrice = tripEntity.getRoute().getBasePrice();
        BigDecimal price = switch (seatEntity.getCategory()) {
            case STANDARD -> basePrice;
            case BUSINESS -> basePrice.multiply(BigDecimal.valueOf(1.5));
            case FIRST_CLASS -> basePrice.multiply(BigDecimal.valueOf(2.0));
        };

        SeatDto response = new SeatDto();
        response.setId(seatEntity.getId());
        response.setSeatNumber(seatEntity.getSeatNumber());
        response.setCategory(seatEntity.getCategory());
        response.setStatus(seatEntity.getStatus());
        response.setPrice(price);
        return response;
    }

    private CarDto toCarDto(Integer carNumber, List<SeatEntity> seatsEntity, TripEntity tripEntity) {
        List<SeatDto> seatsDto = seatsEntity.stream()
                .sorted(Comparator.comparing(SeatEntity::getSeatNumber, Comparator.naturalOrder()))
                .map(seat -> toSeatDto(seat, tripEntity))
                .toList();

        List<SeatCategoryEnum> categories = seatsEntity.stream()
                .map(SeatEntity::getCategory)
                .distinct()
                .sorted()
                .toList();

        CarDto carDto = new CarDto();
        carDto.setCarNumber(carNumber);
        carDto.setCategories(categories);
        carDto.setSeats(seatsDto);
        return carDto;
    }
}
