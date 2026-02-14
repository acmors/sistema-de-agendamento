package com.sistesmareserva.dto.payment;

import com.sistesmareserva.model.enums.PaymentMethod;

import java.math.BigDecimal;

public record CreatePaymentDTO(

        Long reservationId,
        PaymentMethod method

) {
}
