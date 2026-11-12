package com.example.controller;

import com.example.model.dto.response.StationResponse;
import com.example.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @Operation(summary = "Find all stations")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping
    public ResponseEntity<List<StationResponse>> findAll() {
        return ResponseEntity.ok(stationService.findAll());
    }

    @Operation(summary = "Find all stations by part name or part city")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/search/{str}")
    public ResponseEntity<List<StationResponse>> findAllByPartNameOrCity(@PathVariable("str") String str) {
        return ResponseEntity.ok(stationService.findAllByPartNameOrCity(str));
    }
}
