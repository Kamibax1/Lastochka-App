package com.example.service;

import com.example.model.dto.request.SearchTripRequest;
import com.example.model.dto.response.TripResponse;
import com.example.model.mapper.EntityMapper;
import com.example.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final EntityMapper mapper;

    public TripService(TripRepository tripRepository, EntityMapper mapper) {
        this.tripRepository = tripRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<TripResponse> searchTrip(SearchTripRequest request) {
        Instant startOfDay = request.getDate();
        Instant endOfDay = request.getDate().plus(1, ChronoUnit.DAYS);
        return tripRepository.searchTrip(request.getOriginId(), request.getDestinationId(), startOfDay, endOfDay).stream()
                .map(mapper::toTripResponse)
                .toList();
    }
}
