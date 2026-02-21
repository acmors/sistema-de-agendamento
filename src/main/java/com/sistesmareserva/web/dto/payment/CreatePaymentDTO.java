package com.sistesmareserva.web.dto.payment;

import com.sistesmareserva.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreatePaymentDTO(

        Long reservationId,

        @NotBlank
        PaymentMethod method

) {
}
