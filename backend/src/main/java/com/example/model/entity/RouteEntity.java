package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "routes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "duration_min", nullable = false)
    private Integer durationMin;

    @Column(name = "distance_km")
    private Float distanceKm;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_station_id", nullable = false)
    private StationEntity originStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private StationEntity destinationStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private TrainEntity train;
}
