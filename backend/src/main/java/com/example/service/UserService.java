package com.example.service;

import com.example.model.dto.request.UserUpdateRequest;
import com.example.model.dto.response.UserResponse;
import com.example.model.entity.UserEntity;
import com.example.model.mapper.EntityMapper;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EntityMapper mapper;

    public UserService(UserRepository userRepository, EntityMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserResponse getCurrentUser(Authentication authentication) {
        UserEntity entity = userRepository.findByUsername(authentication.getName());
        if (entity == null) {
            throw new EntityNotFoundException("User with username '" + authentication.getName() + "' not found");
        }

        return mapper.toUserResponse(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserResponse updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        UserEntity entity = userRepository.findByUserId(userId);
        if (entity == null) {
            throw new EntityNotFoundException("User with id '" + userId + "' not found");
        }
        entity.setFullName(userUpdateRequest.getFullName());
        entity.setPhone(userUpdateRequest.getPhone());
        entity.setAvatarUrl(userUpdateRequest.getAvatarUrl());
        return mapper.toUserResponse(userRepository.save(entity));
    }
}
