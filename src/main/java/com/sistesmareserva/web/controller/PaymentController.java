package com.sistesmareserva.web.controller;

import com.sistesmareserva.web.dto.payment.CreatePaymentDTO;
import com.sistesmareserva.web.dto.payment.ResponsePaymentDTO;
import com.sistesmareserva.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponsePaymentDTO> processPayment(@RequestBody CreatePaymentDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(dto));
    }

    @GetMapping
    public ResponseEntity<List<ResponsePaymentDTO>> getAllPayments(){
        return ResponseEntity.ok().body(paymentService.listAllPayments());
    }
}
