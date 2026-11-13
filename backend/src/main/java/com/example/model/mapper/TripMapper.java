package com.example.model.mapper;

import com.example.model.dto.response.trip.TripResponse;
import com.example.model.dto.response.trip.TripRouteDto;
import com.example.model.dto.response.trip.TripStationDto;
import com.example.model.dto.response.trip.TripTrainDto;
import com.example.model.entity.RouteEntity;
import com.example.model.entity.StationEntity;
import com.example.model.entity.TrainEntity;
import com.example.model.entity.TripEntity;

public class TripMapper {
    public TripResponse toTripResponse(TripEntity entity) {
        TripResponse response = new TripResponse();
        response.setId(entity.getId());
        response.setRoute(toTripRouteResponse(entity.getRoute()));
        response.setScheduleDepTime(entity.getScheduleDepTime());
        response.setScheduleArrTime(entity.getScheduleArrTime());
        response.setActualDepTime(entity.getActualDepTime());
        response.setActualArrTime(entity.getActualArrTime());
        response.setCarCount(entity.getCarCount());
        response.setStatus(entity.getStatus());
        response.setAvailableSeatsCount(entity.getSeats().size());
        return response;
    }

    private TripStationDto toTripStationResponse(StationEntity entity) {
        TripStationDto response = new TripStationDto();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setCity(entity.getCity());
        return response;
    }

    private TripTrainDto toTripTrainResponse(TrainEntity entity) {
        TripTrainDto response = new TripTrainDto();
        response.setId(entity.getId());
        response.setModel(entity.getModel());
        response.setCapacity(entity.getCapacity());
        response.setWifi(entity.getWifi());
        response.setPowerOutlets(entity.getPowerOutlets());
        response.setBikeSpace(entity.getBikeSpace());
        return response;
    }

    private TripRouteDto toTripRouteResponse(RouteEntity entity) {
        TripRouteDto response = new TripRouteDto();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setOriginStation(toTripStationResponse(entity.getOriginStation()));
        response.setDestinationStation(toTripStationResponse(entity.getDestinationStation()));
        response.setDurationMin(entity.getDurationMin());
        response.setDistanceKm(entity.getDistanceKm());
        response.setBasePrice(entity.getBasePrice());
        response.setTrain(toTripTrainResponse(entity.getTrain()));
        return response;
    }
}
