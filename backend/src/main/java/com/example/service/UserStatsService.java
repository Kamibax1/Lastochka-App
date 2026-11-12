package com.example.service;

import com.example.model.dto.response.UserStatsResponse;
import com.example.model.entity.RouteEntity;
import com.example.model.entity.TicketEntity;
import com.example.model.entity.TripEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.TicketStatusEnum;
import com.example.repository.TicketRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserStatsService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public UserStatsService(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserStatsResponse getUserStats(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException("User with id '" + userId + "' not found");
        }
        List<TicketEntity> tickets = ticketRepository.findAllByUserId(user.getId());

        UserStatsResponse response = new UserStatsResponse();
        response.setTotalTickets(tickets.size());
        response.setCompletedTickets(getTicketsCountByStatus(tickets, TicketStatusEnum.COMPLETED));
        response.setCancelledTickets(getTicketsCountByStatus(tickets, TicketStatusEnum.CANCELLED));
        response.setExpiredTickets(getTicketsCountByStatus(tickets, TicketStatusEnum.EXPIRED));
        response.setActiveTickets(
                getTicketsCountByStatus(tickets, TicketStatusEnum.ACTIVE) +
                        getTicketsCountByStatus(tickets, TicketStatusEnum.PAID)
        );
        response.setTotalDistanceKm(tickets.stream()
                .map(TicketEntity::getTrip)
                .map(TripEntity::getRoute)
                .map(RouteEntity::getDistanceKm)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        response.setTotalSpent(tickets.stream()
                .map(TicketEntity::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return response;
    }

    private int getTicketsCountByStatus(List<TicketEntity> tickets, TicketStatusEnum status) {
        return tickets.stream()
                .filter(ticket -> ticket.getStatus() == status)
                .toList()
                .size();
    }
}
