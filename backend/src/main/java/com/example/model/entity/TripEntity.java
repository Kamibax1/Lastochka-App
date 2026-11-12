package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.model.enums.TripStatusEnum;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trips")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "schedule_dep_time", nullable = false)
    private Instant scheduleDepTime;

    @Column(name = "schedule_arr_time", nullable = false)
    private Instant scheduleArrTime;

    @Column(name = "actual_dep_time")
    private Instant actualDepTime;

    @Column(name = "actual_arr_time")
    private Instant actualArrTime;

    @Column(name = "car_count", nullable = false)
    private Integer carCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatusEnum status = TripStatusEnum.SCHEDULED;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<SeatEntity> seats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;
}
