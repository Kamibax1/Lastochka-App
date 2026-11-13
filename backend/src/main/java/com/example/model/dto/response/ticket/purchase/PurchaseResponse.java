package com.example.model.dto.response.ticket.purchase;

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
public class PurchaseResponse {
    private UUID id;
    private TicketStatusEnum status;
    private BigDecimal totalPrice;
    private Instant purchasedAt;
    private Instant paidAt;
    private Instant expiredAt;
    private PurchaseUserDto user;
    private PurchaseTripDto trip;
    private PurchaseSeatDto seat;
    private Instant createdAt;
    private Instant updatedAt;
}
