package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "stations")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(name = "is_active")
    private Boolean isActive;
}
