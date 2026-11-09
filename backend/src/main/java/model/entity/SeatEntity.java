package model.entity;

import jakarta.persistence.*;
import model.enums.SeatCategoryEnum;
import model.enums.SeatStatusEnum;

import java.util.UUID;

@Entity
@Table(name = "seats")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer carNumber;

    @Column(nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatCategoryEnum category;

    @Enumerated(EnumType.STRING)
    private SeatStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity trip;
}
