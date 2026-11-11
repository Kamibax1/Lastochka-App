package com.example.repository;

import com.example.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TripRepository extends JpaRepository<TripEntity, Long> {
    @Query("SELECT t FROM TripEntity t WHERE " +
            "t.route.originStation.id = :originId AND " +
            "t.route.destinationStation.id = :destinationId AND " +
            "t.scheduleDepTime >= :startOfDay AND " +
            "t.scheduleDepTime < :endOfDay")
    List<TripEntity> searchTrip(
            @Param("originId") UUID originId,
            @Param("destinationId") UUID destinationId,
            @Param("startOfDay") Timestamp startOfDay,
            @Param("endOfDat") Timestamp endOfDay);
}
