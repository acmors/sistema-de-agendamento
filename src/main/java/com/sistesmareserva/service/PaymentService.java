package com.sistesmareserva.service;

import com.sistesmareserva.model.Payment;
import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.model.enums.PaymentMethod;
import com.sistesmareserva.model.enums.PaymentStatus;
import com.sistesmareserva.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    public final PaymentRepository paymentRepository;
    public final ReservationService reservationService;

    @Transactional
    public Payment processPayment(Long reservationId, PaymentMethod method){

        Reservation reservation = reservationService.findById(reservationId);

        Payment payment = new Payment();
        payment.setValue(reservation.getTotalValue());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(method);
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        payment.setReservation(reservation);

        return paymentRepository.save(payment);

    }
}
