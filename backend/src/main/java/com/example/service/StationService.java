package com.example.service;

import com.example.model.dto.response.StationResponse;
import com.example.model.mapper.EntityMapper;
import com.example.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final EntityMapper mapper;

    public StationService(StationRepository stationRepository, EntityMapper mapper) {
        this.stationRepository = stationRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<StationResponse> findAll() {
        return stationRepository.findAll().stream()
                .map(mapper::toStationResponse)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<StationResponse> findAllByPartNameOrCity(String str) {
        return stationRepository.searchByNameOrCity(str).stream()
                .map(mapper::toStationResponse)
                .toList();
    }
}
