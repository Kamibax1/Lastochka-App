package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "trains")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer capacity;

    private String description;

    @Column(nullable = false)
    private Boolean wifi = false;

    @Column(name = "power_outlets", nullable = false)
    private Integer powerOutlets = 0;

    @Column(name = "bike_space", nullable = false)
    private Boolean bikeSpace = false;
}
