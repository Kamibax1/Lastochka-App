package com.example.service;

import com.example.exception.BusinessException;
import com.example.exception.SeatAlreadyTakenException;
import com.example.model.dto.request.PurchaseRequest;
import com.example.model.dto.response.ticket.purchase.PurchaseResponse;
import com.example.model.entity.SeatEntity;
import com.example.model.entity.TicketEntity;
import com.example.model.entity.TripEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.SeatStatusEnum;
import com.example.model.enums.TicketStatusEnum;
import com.example.model.enums.TripStatusEnum;
import com.example.model.mapper.PurchaseMapper;
import com.example.repository.SeatRepository;
import com.example.repository.TicketRepository;
import com.example.repository.TripRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class PurchaseService {
    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper mapper;

    public PurchaseService(TicketRepository ticketRepository, TripRepository tripRepository, SeatRepository seatRepository, UserRepository userRepository, PurchaseMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PurchaseResponse purchase(PurchaseRequest request) {
        TripEntity tripEntity = checkEntity(tripRepository, request.getTripId());
        UserEntity userEntity = checkEntity(userRepository, request.getUserId());
        SeatEntity seatEntity = seatRepository.findByIdForUpdate(request.getSeatId());
        if (seatEntity == null) {
            throw new EntityNotFoundException("Seat with id " + request.getSeatId() + " not found");
        }
        if (tripEntity.getStatus() != TripStatusEnum.SCHEDULED && tripEntity.getStatus() != TripStatusEnum.BOARDING) {
            throw new BusinessException("Рейс не доступен");
        }
        if (!seatEntity.getTrip().getId().equals(tripEntity.getId())) {
            throw new BusinessException("Место не принадлежит выбранному рейсу");
        }
        if (seatEntity.getStatus() != SeatStatusEnum.FREE) {
            throw new SeatAlreadyTakenException();
        }

        BigDecimal basePrice = seatEntity.getTrip().getRoute().getBasePrice();
        BigDecimal price = switch (seatEntity.getCategory()) {
            case STANDARD -> basePrice;
            case BUSINESS -> basePrice.multiply(BigDecimal.valueOf(1.5));
            case FIRST_CLASS -> basePrice.multiply(BigDecimal.valueOf(2.0));
        };
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setStatus(TicketStatusEnum.AWAITING_PAYMENT);
        ticketEntity.setTotalAmount(price);
        ticketEntity.setPurchasedAt(Instant.now());
        ticketEntity.setExpiredAt(Instant.now().plus(Duration.ofMinutes(15)));
        ticketEntity.setUser(userEntity);
        ticketEntity.setTrip(tripEntity);
        ticketEntity.setSeat(seatEntity);
        TicketEntity savedTicket = ticketRepository.save(ticketEntity);

        seatEntity.setStatus(SeatStatusEnum.RESERVED);
        seatEntity.setReservedUntil(ticketEntity.getExpiredAt());
        seatRepository.save(seatEntity);

        return mapper.toPurchaseResponse(savedTicket);
    }

    private <T> T checkEntity(JpaRepository<T, UUID> repository, UUID id) {
        T entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException(repository.getClass().getSimpleName() + " with id '" + id + "' not found");
        }
        return entity;
    }
}
