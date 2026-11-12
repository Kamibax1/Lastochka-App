package com.example.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsResponse {
    private Integer totalTickets;
    private Integer completedTickets;
    private Integer cancelledTickets;
    private Integer expiredTickets;
    private Integer activeTickets;
    private BigDecimal totalDistanceKm;
    private BigDecimal totalSpent;
}
