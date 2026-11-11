package com.example.repository;

import com.example.model.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StationRepository extends JpaRepository<StationEntity, UUID> {
    @Query("SELECT s FROM StationEntity s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :str, '%')) OR " +
            "LOWER(s.city) LIKE LOWER(CONCAT('%', :str, '%'))")
    List<StationEntity> searchByNameOrCity(@Param("str") String str);
}
