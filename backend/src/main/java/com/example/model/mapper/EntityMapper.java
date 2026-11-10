package com.example.model.mapper;

import com.example.model.dto.request.RegisterRequest;
import com.example.model.dto.response.UserResponse;
import com.example.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public UserEntity toUserEntity(RegisterRequest registerRequest) {
        UserEntity entity = new UserEntity();
        entity.setFullName(registerRequest.getFullName());
        entity.setUsername(registerRequest.getUsername());
        entity.setEmail(registerRequest.getEmail());
        entity.setPhone(registerRequest.getPhone());
        return entity;
    }

    public UserResponse toUserResponse(UserEntity userEntity) {
        UserResponse response = new UserResponse();
        response.setId(userEntity.getId());
        response.setFullName(userEntity.getFullName());
        response.setUsername(userEntity.getUsername());
        response.setEmail(userEntity.getEmail());
        response.setRole(userEntity.getRole());
        return response;
    }
}
