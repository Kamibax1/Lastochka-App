package com.example.service;

import com.example.model.dto.request.SearchTripRequest;
import com.example.model.dto.response.trip.TripResponse;
import com.example.model.dto.response.trip.seat.SeatMapResponse;
import com.example.model.entity.TripEntity;
import com.example.model.mapper.SeatMapMapper;
import com.example.model.mapper.TripMapper;
import com.example.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final SeatMapMapper seatMapMapper;

    public TripService(TripRepository tripRepository, TripMapper mapper, SeatMapMapper seatMapMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = mapper;
        this.seatMapMapper = seatMapMapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<TripResponse> searchTrip(SearchTripRequest request) {
        Instant startOfDay = request.getDate();
        Instant endOfDay = request.getDate().plus(1, ChronoUnit.DAYS);
        return tripRepository.searchTrip(request.getOriginId(), request.getDestinationId(), startOfDay, endOfDay).stream()
                .map(tripMapper::toTripResponse)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TripResponse findById(UUID tripId) {
        TripEntity trip = checkTripById(tripId);
        return tripMapper.toTripResponse(trip);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SeatMapResponse getSeatMap(UUID tripId) {
        TripEntity trip = checkTripById(tripId);
        return seatMapMapper.toSeatMapResponse(trip);
    }

    private TripEntity checkTripById(UUID tripId) {
        TripEntity trip = tripRepository.findById(tripId).orElse(null);
        if (trip == null) {
            throw new EntityNotFoundException("Trip with id '" + tripId + "' not found");
        }
        return trip;
    }
}
