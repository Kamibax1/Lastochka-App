package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.model.enums.TripStatusEnum;

import java.sql.Timestamp;
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
    private Timestamp scheduleDepTime;

    @Column(name = "schedule_arr_time", nullable = false)
    private Timestamp scheduleArrTime;

    @Column(name = "actual_dep_time")
    private Timestamp actualDepTime;

    @Column(name = "car_count", nullable = false)
    private Integer carCount;

    @Enumerated(EnumType.STRING)
    private TripStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;
}
