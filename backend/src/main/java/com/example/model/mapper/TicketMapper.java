package com.example.model.mapper;

import com.example.model.dto.response.ticket.info.full.*;
import com.example.model.dto.response.ticket.info.shorts.TicketShortResponse;
import com.example.model.dto.response.ticket.info.shorts.TicketShortSeatDto;
import com.example.model.dto.response.ticket.info.shorts.TicketShortTripDto;
import com.example.model.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketShortResponse toTicketShortResponse(TicketEntity entity) {
        TicketShortResponse response = new TicketShortResponse();
        response.setId(entity.getId());
        response.setTotalAmount(entity.getTotalAmount());
        response.setPurchasedAt(entity.getPurchasedAt());
        response.setPaidAt(entity.getPaidAt());
        response.setPassengerFullName(entity.getUser().getFullName());
        response.setTrip(toTicketShortTripDto(entity.getTrip()));
        response.setSeat(toTicketShortSeatDto(entity.getSeat()));
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }

    private TicketShortTripDto toTicketShortTripDto(TripEntity entity) {
        TicketShortTripDto dto = new TicketShortTripDto();
        dto.setId(entity.getId());
        dto.setRouteName(entity.getRoute().getName());
        dto.setOriginStationName(entity.getRoute().getOriginStation().getName());
        dto.setDestinationStationName(entity.getRoute().getDestinationStation().getName());
        dto.setScheduleDepTime(entity.getScheduleDepTime());
        dto.setScheduleArrTime(entity.getScheduleArrTime());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private TicketShortSeatDto toTicketShortSeatDto(SeatEntity entity) {
        TicketShortSeatDto dto = new TicketShortSeatDto();
        dto.setId(entity.getId());
        dto.setCarNumber(entity.getCarNumber());
        dto.setSeatNumber(entity.getSeatNumber());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public TicketFullResponse toTicketFullResponse(TicketEntity entity) {
        TicketFullResponse response = new TicketFullResponse();
        response.setId(entity.getId());
        response.setStatus(entity.getStatus());
        response.setTotalAmount(entity.getTotalAmount());
        response.setPurchasedAt(entity.getPurchasedAt());
        response.setPaidAt(entity.getPaidAt());
        response.setExpiresAt(entity.getExpiredAt());
        response.setUser(toTicketFullUserDto(entity.getUser()));
        response.setTrip(toTicketFullTripDto(entity.getTrip()));
        response.setSeat(toTicketFullSeatDto(entity.getSeat()));
        response.setCanPay(entity.getPaidAt() != null);
        response.setCanCancel(entity.getStatus().name().equals("CANCELLED"));
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
    private TicketFullUserDto toTicketFullUserDto(UserEntity entity) {
        TicketFullUserDto dto = new TicketFullUserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setFullName(entity.getFullName());
        dto.setAvatarUrl(entity.getAvatarUrl());
        return dto;
    }

    private TicketFullTripDto toTicketFullTripDto(TripEntity entity) {
        TicketFullTripDto dto = new TicketFullTripDto();
        dto.setId(entity.getId());
        dto.setRoute(toTicketFullRouteDto(entity.getRoute()));
        dto.setScheduleDepTime(entity.getScheduleDepTime());
        dto.setScheduleArrTime(entity.getScheduleArrTime());
        dto.setActualDepTime(entity.getActualDepTime());
        dto.setActualArrTime(entity.getActualArrTime());
        dto.setCarCount(entity.getCarCount());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private TicketFullRouteDto toTicketFullRouteDto(RouteEntity entity) {
        TicketFullRouteDto dto = new TicketFullRouteDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOriginStation(toTicketFullStationDto(entity.getOriginStation()));
        dto.setDestinationStation(toTicketFullStationDto(entity.getDestinationStation()));
        dto.setDurationMin(entity.getDurationMin());
        dto.setDistanceKm(entity.getDistanceKm());
        dto.setTrain(toTicketFullTrainDto(entity.getTrain()));
        return dto;
    }

    private TicketFullStationDto toTicketFullStationDto(StationEntity entity) {
        TicketFullStationDto dto = new TicketFullStationDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setCity(entity.getCity());
        return dto;
    }

    private TicketFullTrainDto toTicketFullTrainDto(TrainEntity entity) {
        TicketFullTrainDto dto = new TicketFullTrainDto();
        dto.setId(entity.getId());
        dto.setModel(entity.getModel());
        dto.setWifi(entity.getWifi());
        dto.setPowerOutlets(entity.getPowerOutlets());
        dto.setBikeSpace(entity.getBikeSpace());
        return dto;
    }

    private TicketFullSeatDto toTicketFullSeatDto(SeatEntity entity) {
        TicketFullSeatDto dto = new TicketFullSeatDto();
        dto.setId(entity.getId());
        dto.setCarNumber(entity.getCarNumber());
        dto.setSeatNumber(entity.getSeatNumber());
        dto.setCategory(entity.getCategory());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
