package com.example.controller;

import com.example.model.dto.request.PurchaseRequest;
import com.example.model.dto.response.ticket.info.full.TicketFullResponse;
import com.example.model.dto.response.ticket.info.shorts.TicketShortResponse;
import com.example.model.dto.response.ticket.purchase.PurchaseResponse;
import com.example.model.enums.TicketStatusEnum;
import com.example.service.PurchaseService;
import com.example.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets")
public class TicketController {
    private final PurchaseService purchaseService;
    private final TicketService ticketService;

    public TicketController(PurchaseService purchaseService, TicketService ticketService) {
        this.purchaseService = purchaseService;
        this.ticketService = ticketService;
    }

    @Operation(summary = "Purchase ticket")
    @SecurityRequirement(name = "bearer-jwt")
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchase(@Valid @RequestBody PurchaseRequest request){
        return ResponseEntity.ok(purchaseService.purchase(request));
    }

    @Operation(summary = "Get all of user tickets")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/user")
    public ResponseEntity<List<TicketShortResponse>> findAllByUser(Authentication authentication){
        List<TicketShortResponse> tickets = ticketService.findAllByUser(authentication);
        if(tickets.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Get all of user tickets by status")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/user/status/{status}")
    public ResponseEntity<List<TicketShortResponse>> findAllByStatus(@PathVariable("status") TicketStatusEnum status, Authentication authentication){
        List<TicketShortResponse> tickets = ticketService.findAllByUserByStatus(status, authentication);
        if(tickets.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Get full info of user ticket by id")
    @SecurityRequirement(name = "bearer-jwt")
    @GetMapping("/user/{id}")
    public ResponseEntity<TicketFullResponse> findByIdByUser(@PathVariable("id") UUID id, Authentication authentication){
        return ResponseEntity.ok(ticketService.findByIdByUser(id, authentication));
    }

    @Operation(summary = "Pay ticket")
    @SecurityRequirement(name = "bearer-jwt")
    @PostMapping("/user/{ticketId}/pay")
    public ResponseEntity<TicketShortResponse> payTicket(@PathVariable("id") UUID id, Authentication authentication){
        return ResponseEntity.ok(purchaseService.payTicket(id, authentication));
    }

    @Operation(summary = "Cancel pay ticket")
    @SecurityRequirement(name = "bearer-jwt")
    @PostMapping("/user/{ticketId}/cancel")
    public ResponseEntity<TicketShortResponse> cancelTicket(@PathVariable("ticketId") UUID id, Authentication authentication){
        return ResponseEntity.ok(purchaseService.cancelPay(id, authentication));
    }
}
