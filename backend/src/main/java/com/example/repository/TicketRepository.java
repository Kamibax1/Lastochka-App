package com.example.repository;

import com.example.model.entity.TicketEntity;
import com.example.model.enums.TicketStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    List<TicketEntity> findAllByUserId(UUID userId);
}
