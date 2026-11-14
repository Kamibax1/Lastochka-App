package com.example.model.dto.response.ticket.info.shorts;

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
public class TicketShortResponse {
    private UUID id;
    private TicketStatusEnum status;
    private BigDecimal totalAmount;
    private Instant purchasedAt;
    private Instant paidAt;
    private String passengerFullName;
    private TicketShortTripDto trip;
    private TicketShortSeatDto seat;
    private Instant createdAt;
}
