package com.example.model.mapper;

import com.example.model.dto.request.RegisterRequest;
import com.example.model.dto.response.StationResponse;
import com.example.model.dto.response.TripResponse;
import com.example.model.dto.response.UserResponse;
import com.example.model.entity.StationEntity;
import com.example.model.entity.TripEntity;
import com.example.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public UserEntity toUserEntity(RegisterRequest request) {
        UserEntity entity = new UserEntity();
        entity.setFullName(request.getFullName());
        entity.setUsername(request.getUsername());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        return entity;
    }

    public UserResponse toUserResponse(UserEntity entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setFullName(entity.getFullName());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setRole(entity.getRole());
        return response;
    }

    public StationResponse toStationResponse(StationEntity entity) {
        StationResponse response = new StationResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setCity(entity.getCity());
        return response;
    }

    public TripResponse toTripResponse(TripEntity entity) {
        TripResponse response = new TripResponse();
        response.setId(entity.getId());
        response.setRouteName(entity.getRoute().getName());
        response.setTrainModel(entity.getRoute().getTrain().getModel());
        response.setDepartureTime(entity.getScheduleDepTime());
        response.setArrivalTime(entity.getScheduleArrTime());
        response.setDurationMinutes(entity.getRoute().getDurationMin());
        response.setStatus(entity.getStatus());
        response.setAvailableSeatsCount(entity.getSeats().size());
        response.setBasePrice(entity.getRoute().getBasePrice());
        return response;
    }
}
