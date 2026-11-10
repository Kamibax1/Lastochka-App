package com.example.model.entity;

import jakarta.persistence.*;
import com.example.model.enums.SeatCategoryEnum;
import com.example.model.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private SeatCategoryEnum category;

    @Enumerated(EnumType.STRING)
    private SeatStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity trip;
}
