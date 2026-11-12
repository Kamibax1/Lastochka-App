package com.example.model.entity;

import jakarta.persistence.*;
import com.example.model.enums.SeatCategoryEnum;
import com.example.model.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "seats")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "car_number", nullable = false)
    private Integer carNumber;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatCategoryEnum category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatusEnum status = SeatStatusEnum.FREE;

    @Column(name = "reserved_until")
    private Instant reservedUntil;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity trip;
}
