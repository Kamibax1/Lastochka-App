package com.example.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
public class SearchTripRequest {
    @NotNull
    @JsonProperty("origin_id")
    private UUID originId;

    @NotNull
    @JsonProperty("destination_id")
    private UUID destinationId;

    @NotNull
    private Instant date;
}
