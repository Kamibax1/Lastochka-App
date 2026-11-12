package com.example.controller;

import com.example.model.dto.request.SearchTripRequest;
import com.example.model.dto.response.trip.TripResponse;
import com.example.model.dto.response.trip.seat.SeatMapResponse;
import com.example.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @Operation(summary = "Search Trip by origin station id, destination station id and date")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/search")
    public ResponseEntity<List<TripResponse>> searchTrip(@RequestBody SearchTripRequest request) {
        return ResponseEntity.ok(tripService.searchTrip(request));
    }

    @Operation(summary = "Find Trip by id")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponse> findTripById(@PathVariable("tripId") UUID tripId) {
        return ResponseEntity.ok(tripService.findById(tripId));
    }

    @Operation(summary = "Get seat map by trip id")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/{tripId}/seat-map")
    public ResponseEntity<SeatMapResponse> getSeatMap(@PathVariable("tripId") UUID tripId) {
        return ResponseEntity.ok(tripService.getSeatMap(tripId));
    }
}
