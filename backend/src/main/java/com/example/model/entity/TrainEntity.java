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
    private String capacity;

    private String description;
    private boolean wifi;

    @Column(name = "power_outlets")
    private int powerOutlets;

    @Column(name = "bike_space")
    private boolean bikeSpace;
}
