package com.sistesmareserva.service;

import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public Reservation create(Reservation createReservation){
        return reservationRepository.save(createReservation);
    }

    @Transactional(readOnly = true)
    public Reservation findById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("entity not found"));
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation update(Long id, Reservation newReservation){
        Reservation reservation = findById(id);

        reservation.setCheckingDate(newReservation.getCheckingDate());
        reservation.setCheckoutDate(newReservation.getCheckoutDate());
        reservation.setTotalValue(newReservation.getTotalValue());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteById(Long id){
        reservationRepository.deleteById(id);
    }

}
