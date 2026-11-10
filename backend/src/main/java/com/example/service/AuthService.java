package com.example.service;

import com.example.model.dto.request.LoginRequest;
import com.example.model.dto.request.RegisterRequest;
import com.example.model.dto.response.AuthResponse;
import com.example.model.dto.response.UserResponse;
import com.example.model.entity.UserEntity;
import com.example.model.enums.RoleEnum;
import com.example.model.mapper.EntityMapper;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EntityMapper mapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, EntityMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity entity = userRepository.findByUsername(request.getUsername());
        if (entity == null) {
            throw new EntityNotFoundException("User with username '" + request.getUsername() + "' not found");
        }

        String token = jwtService.generateToken(entity);
        entity.setLastLoginAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(entity);
        UserResponse userResponse = mapper.toUserResponse(entity);

        return new AuthResponse(token, userResponse);
    }

    public AuthResponse register(RegisterRequest request) throws ValidationException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ValidationException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ValidationException("Phone already exists");
        }
        UserEntity entity = mapper.toUserEntity(request);
        entity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        entity.setRole(RoleEnum.USER);

        UserEntity saved = userRepository.save(entity);
        String token = jwtService.generateToken(saved);
        UserResponse userResponse = mapper.toUserResponse(saved);

        return new AuthResponse(token, userResponse);
    }
}
