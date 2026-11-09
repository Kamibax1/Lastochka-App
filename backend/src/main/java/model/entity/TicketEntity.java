package model.entity;

import jakarta.persistence.*;
import model.enums.TicketStatusEnum;

import java.sql.Timestamp;

@Entity
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;

    private TicketStatusEnum status;
    @Column(nullable = false)
    private Double totalAmount;

    private Timestamp purchasedAt;
    private Timestamp paidAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity trip;
}
