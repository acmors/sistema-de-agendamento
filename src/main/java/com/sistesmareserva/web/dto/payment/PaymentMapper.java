package com.sistesmareserva.web.dto.payment;

import com.sistesmareserva.model.Payment;

public class PaymentMapper {

    public static ResponsePaymentDTO toDTO(Payment payment){
        return new ResponsePaymentDTO(
                payment.getValue(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus()
        );
    }
}
