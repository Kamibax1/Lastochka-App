package com.example.model.mapper;

import com.example.model.dto.request.RegisterRequest;
import com.example.model.dto.response.StationResponse;
import com.example.model.dto.response.UserResponse;
import com.example.model.entity.StationEntity;
import com.example.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.*;

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
        response.setPhone(entity.getPhone());
        response.setAvatarUrl(entity.getAvatarUrl());
        response.setCreatedAt(entity.getCreatedAt());
        response.setLastLoginAt(entity.getLastLoginAt());
        return response;
    }

    public StationResponse toStationResponse(StationEntity entity) {
        StationResponse response = new StationResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setCity(entity.getCity());
        response.setIsActive(entity.getIsActive());
        return response;
    }
}
