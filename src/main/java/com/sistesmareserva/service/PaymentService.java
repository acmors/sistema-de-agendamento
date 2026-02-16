package com.sistesmareserva.service;

import com.sistesmareserva.exception.ConflictException;
import com.sistesmareserva.exception.StatusAlreadyExistsException;
import com.sistesmareserva.web.dto.payment.CreatePaymentDTO;
import com.sistesmareserva.web.dto.payment.PaymentMapper;
import com.sistesmareserva.web.dto.payment.ResponsePaymentDTO;
import com.sistesmareserva.model.Payment;
import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.model.enums.PaymentStatus;
import com.sistesmareserva.model.enums.ReservationStatus;
import com.sistesmareserva.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    public final PaymentRepository paymentRepository;
    public final ReservationService reservationService;

    @Transactional
    public ResponsePaymentDTO processPayment(CreatePaymentDTO dto){

        Reservation reservation = reservationService.findById(dto.reservationId());

        if (reservation.getReservationStatus() == ReservationStatus.CANCELED) throw new ConflictException("Reservation status is cancelled");
        if (reservation.getReservationStatus() == ReservationStatus.APPROVED) throw new ConflictException("Reservation already paid.");

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setValue(reservation.getTotalValue());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(dto.method());
        payment.setPaymentStatus(PaymentStatus.APPROVED);

        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.toDTO(saved);

    }

    @Transactional(readOnly = true)
    public List<ResponsePaymentDTO> listAllPayments(){
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toDTO)
                .toList();
    }
}
