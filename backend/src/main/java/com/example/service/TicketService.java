package com.example.service;

import com.example.exception.BusinessException;
import com.example.exception.ConflictException;
import com.example.model.dto.response.ticket.info.full.TicketFullResponse;
import com.example.model.dto.response.ticket.info.shorts.TicketShortResponse;
import com.example.model.entity.TicketEntity;
import com.example.model.enums.SeatStatusEnum;
import com.example.model.enums.TicketStatusEnum;
import com.example.model.mapper.TicketMapper;
import com.example.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;

    public TicketService(TicketRepository ticketRepository, TicketMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<TicketShortResponse> findAllByUser(Authentication authentication) {
        return ticketRepository.findAllByUserUsername(authentication.getName()).stream()
                .map(mapper::toTicketShortResponse)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<TicketShortResponse> findAllByUserByStatus(TicketStatusEnum status, Authentication authentication) {
        return ticketRepository.findAllByUserUsernameAndStatus(authentication.getName(), status).stream()
                .map(mapper::toTicketShortResponse)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public TicketFullResponse findByIdByUser(UUID id, Authentication authentication) {
        TicketEntity entity = ticketRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException("Ticket with id '" + id + "' not found");
        }
        String username = authentication.getName();
        if(!entity.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("User with username '" + username + "' is not allowed to access ticket with id '" + id + "'");
        }
        return mapper.toTicketFullResponse(entity);
    }
}
