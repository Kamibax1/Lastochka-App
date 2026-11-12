package com.example.controller;

import com.example.model.dto.request.SearchTripRequest;
import com.example.model.dto.response.TripResponse;
import com.example.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
