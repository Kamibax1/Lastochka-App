package com.example.service;

import com.example.exception.BusinessException;
import com.example.exception.ConflictException;
import com.example.model.dto.request.PurchaseRequest;
import com.example.model.dto.response.ticket.info.shorts.TicketShortResponse;
import com.example.model.dto.response.ticket.purchase.PurchaseResponse;
import com.example.model.entity.SeatEntity;
import com.example.model.entity.TicketEntity;
import com.example.model.entity.TripEntity;
import com.example.model.entity.UserEntity;
import com.example.model.enums.SeatStatusEnum;
import com.example.model.enums.TicketStatusEnum;
import com.example.model.enums.TripStatusEnum;
import com.example.model.mapper.PurchaseMapper;
import com.example.model.mapper.TicketMapper;
import com.example.repository.SeatRepository;
import com.example.repository.TicketRepository;
import com.example.repository.TripRepository;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
    private final PurchaseMapper purchaseMapper;
    private final TicketMapper ticketMapper;

    public PurchaseService(TicketRepository ticketRepository, TripRepository tripRepository, SeatRepository seatRepository, UserRepository userRepository, PurchaseMapper purchaseMapper, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.purchaseMapper = purchaseMapper;
        this.ticketMapper = ticketMapper;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PurchaseResponse purchase(PurchaseRequest request) {
        TripEntity tripEntity = checkEntity(tripRepository, request.getTripId());
        UserEntity userEntity = checkEntity(userRepository, request.getUserId());
        SeatEntity seatEntity = checkSeatEntity(request.getSeatId());
        if (tripEntity.getStatus() != TripStatusEnum.SCHEDULED &&
            tripEntity.getStatus() != TripStatusEnum.BOARDING) {
            throw new BusinessException("Рейс не доступен");
        }
        if (seatEntity.getTrip().getId() != tripEntity.getId()) {
            throw new BusinessException("Место не принадлежит выбранному рейсу");
        }
        if (seatEntity.getStatus() != SeatStatusEnum.FREE) {
            throw new ConflictException("Место уже занято");
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

        return purchaseMapper.toPurchaseResponse(savedTicket);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TicketShortResponse payTicket(UUID id, Authentication authentication) {
        TicketEntity ticketEntity = checkEntity(ticketRepository, id);
        checkTicketOfUser(ticketEntity, authentication);
        TicketStatusEnum ticketStatus = ticketEntity.getStatus();
        SeatStatusEnum seatStatus = ticketEntity.getSeat().getStatus();
        if(ticketStatus.equals(TicketStatusEnum.PAID)) {
            if(ticketEntity.getSeat().getStatus() != SeatStatusEnum.SOLD) {
                throw new ConflictException("Состояние билета и места рассинхронизированы. Билет: '" +
                        ticketStatus + "Место: '" + seatStatus + "'");
            }
            return ticketMapper.toTicketShortResponse(ticketEntity);
        }
        if(ticketStatus != TicketStatusEnum.AWAITING_PAYMENT){
            throw new BusinessException("Билет нельзя оплатить в текущем статусе");
        }
        Instant now = Instant.now();
        if (ticketEntity.getExpiredAt() == null || ticketEntity.getExpiredAt().isBefore(now)) {
            throw new BusinessException("Время оплаты истекло");
        }
        SeatEntity seatEntity = checkSeatEntity(ticketEntity.getSeat().getId());
        if (seatEntity.getStatus() != SeatStatusEnum.RESERVED) {
            throw new ConflictException("Место больше не зарезервировано");
        }
        if (seatEntity.getTrip().getId() != ticketEntity.getTrip().getId()) {
            throw new ConflictException("Место не принадлежит рейсу билета");
        }
        ticketEntity.setStatus(TicketStatusEnum.PAID);
        ticketEntity.setPaidAt(now);
        ticketEntity.setExpiredAt(null);
        seatEntity.setStatus(SeatStatusEnum.SOLD);
        seatEntity.setReservedUntil(null);
        return ticketMapper.toTicketShortResponse(ticketEntity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TicketShortResponse cancelPay(UUID id, Authentication authentication) {
        TicketEntity ticketEntity = checkEntity(ticketRepository, id);
        checkTicketOfUser(ticketEntity, authentication);
        if (ticketEntity.getStatus() == TicketStatusEnum.CANCELLED) {
            return ticketMapper.toTicketShortResponse(ticketEntity);
        }
        if (ticketEntity.getStatus() != TicketStatusEnum.AWAITING_PAYMENT &&
            ticketEntity.getStatus() != TicketStatusEnum.PAID) {
            throw new BusinessException("Билет нельзя отменить в текущем статусе");
        }

        TripEntity tripEntity = checkEntity(tripRepository, ticketEntity.getTrip().getId());
        TripStatusEnum tripStatus = tripEntity.getStatus();
        if (tripStatus == TripStatusEnum.IN_TRANSIT ||
            tripStatus == TripStatusEnum.ARRIVED) {
            throw new BusinessException("Нельзя отменить билет после начала или завершения поездки");
        }
        if (tripStatus == TripStatusEnum.CANCELLED) {
            throw new BusinessException("Рейс отменен");
        }
        if (tripEntity.getScheduleDepTime().isAfter(Instant.now())) {
            throw new BusinessException("Нельзя отменить билет после времени отправления");
        }
        SeatEntity seatEntity = checkSeatEntity(ticketEntity.getSeat().getId());
        if (seatEntity.getTrip().getId() != ticketEntity.getTrip().getId()) {
            throw new ConflictException("Место не принадлежит рейсу билета");
        }
        if (seatEntity.getStatus() == SeatStatusEnum.RESERVED ||
            seatEntity.getStatus() == SeatStatusEnum.SOLD) {
            seatEntity.setStatus(SeatStatusEnum.FREE);
            seatEntity.setReservedUntil(null);
        } else if (seatEntity.getStatus() != SeatStatusEnum.FREE) {
            throw new ConflictException("Неожиданный статус места при отмене билета");
        }
        ticketEntity.setStatus(TicketStatusEnum.CANCELLED);
        ticketEntity.setExpiredAt(null);
        return ticketMapper.toTicketShortResponse(ticketEntity);
    }

    private <T> T checkEntity(JpaRepository<T, UUID> repository, UUID id) {
        T entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException(repository.getClass().getSimpleName() + " with id '" + id + "' not found");
        }
        return entity;
    }
    private void checkTicketOfUser(TicketEntity entity, Authentication authentication) {
        String username = authentication.getName();
        if(!entity.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("User with username '" + username + "' is not allowed to access ticket with id '" + entity.getId() + "'");
        }
    }
    private SeatEntity checkSeatEntity(UUID id) {
        SeatEntity seatEntity = seatRepository.findByIdForUpdate(id);
        if (seatEntity == null) {
            throw new EntityNotFoundException("Seat with id " + id + " not found");
        }
        return seatEntity;
    }
}
