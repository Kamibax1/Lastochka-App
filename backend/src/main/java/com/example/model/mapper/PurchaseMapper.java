package com.example.model.mapper;

import com.example.model.dto.response.ticket.purchase.PurchaseResponse;
import com.example.model.dto.response.ticket.purchase.PurchaseSeatDto;
import com.example.model.dto.response.ticket.purchase.PurchaseTripDto;
import com.example.model.dto.response.ticket.purchase.PurchaseUserDto;
import com.example.model.entity.SeatEntity;
import com.example.model.entity.TicketEntity;
import com.example.model.entity.TripEntity;
import com.example.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {
    public PurchaseResponse toPurchaseResponse(TicketEntity ticketEntity) {
        PurchaseResponse purchaseResponse = new PurchaseResponse();
        purchaseResponse.setId(ticketEntity.getId());
        purchaseResponse.setTotalPrice(ticketEntity.getTotalAmount());
        purchaseResponse.setPurchasedAt(ticketEntity.getPurchasedAt());
        purchaseResponse.setPaidAt(ticketEntity.getPaidAt());
        purchaseResponse.setExpiredAt(ticketEntity.getExpiredAt());
        purchaseResponse.setUser(toPurchaseUserDto(ticketEntity.getUser()));
        purchaseResponse.setTrip(toPurchaseTripDto(ticketEntity.getTrip()));
        purchaseResponse.setSeat(toPurchaseSeatDto(ticketEntity.getSeat()));
        purchaseResponse.setCreatedAt(ticketEntity.getCreatedAt());
        purchaseResponse.setUpdatedAt(ticketEntity.getUpdatedAt());
        return purchaseResponse;
    }


    private PurchaseUserDto toPurchaseUserDto(UserEntity entity) {
        PurchaseUserDto purchaseUserDto = new PurchaseUserDto();
        purchaseUserDto.setId(entity.getId());
        purchaseUserDto.setUsername(entity.getUsername());
        purchaseUserDto.setFullName(entity.getFullName());
        return purchaseUserDto;
    }

    private PurchaseTripDto toPurchaseTripDto(TripEntity entity) {
        PurchaseTripDto purchaseTripDto = new PurchaseTripDto();
        purchaseTripDto.setId(entity.getId());
        purchaseTripDto.setRouteName(entity.getRoute().getName());
        purchaseTripDto.setOriginStationName(entity.getRoute().getOriginStation().getName());
        purchaseTripDto.setDestinationStationName(entity.getRoute().getDestinationStation().getName());
        purchaseTripDto.setScheduledDepTime(entity.getScheduleDepTime());
        purchaseTripDto.setScheduledArrTime(entity.getScheduleArrTime());
        purchaseTripDto.setStatus(entity.getStatus());
        return purchaseTripDto;
    }

    private PurchaseSeatDto toPurchaseSeatDto(SeatEntity entity) {
        PurchaseSeatDto purchaseSeatDto = new PurchaseSeatDto();
        purchaseSeatDto.setId(entity.getId());
        purchaseSeatDto.setCarNumber(entity.getCarNumber());
        purchaseSeatDto.setSeatNumber(entity.getSeatNumber());
        purchaseSeatDto.setCategory(entity.getCategory());
        purchaseSeatDto.setStatus(entity.getStatus());
        return purchaseSeatDto;
    }
}
