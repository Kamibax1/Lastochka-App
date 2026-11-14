package com.example.model.dto.response.ticket.info.full;

import com.example.model.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketFullResponse {
    private UUID id;
    private TicketStatusEnum status;
    private BigDecimal totalAmount;
    private Instant purchasedAt;
    private Instant paidAt;
    private Instant expiresAt;
    private TicketFullUserDto user;
    private TicketFullTripDto trip;
    private TicketFullSeatDto seat;
    private Boolean canPay;
    private Boolean canCancel;
    private Instant createdAt;
}
