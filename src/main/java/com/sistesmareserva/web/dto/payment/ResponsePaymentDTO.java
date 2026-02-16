package com.sistesmareserva.web.dto.payment;

import com.sistesmareserva.model.enums.PaymentMethod;
import com.sistesmareserva.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponsePaymentDTO(

        BigDecimal value,
        LocalDateTime paymentDate,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus
) {
}
