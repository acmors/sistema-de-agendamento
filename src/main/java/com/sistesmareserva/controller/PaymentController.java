package com.sistesmareserva.controller;

import com.sistesmareserva.model.Payment;
import com.sistesmareserva.model.enums.PaymentMethod;
import com.sistesmareserva.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping
    public ResponseEntity<Payment> processPayment(@PathVariable Long reservationId, @RequestBody PaymentMethod method){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(reservationId, method));
    }
}
