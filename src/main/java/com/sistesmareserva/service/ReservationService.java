package com.sistesmareserva.service;

import com.sistesmareserva.model.Client;
import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.model.Room;
import com.sistesmareserva.model.enums.ReservationStatus;
import com.sistesmareserva.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final RoomService roomService;

    @Transactional
    public Reservation create(Long clientId, Long roomId, LocalDateTime checking, LocalDateTime checkout){

        Client client = clientService.findById(clientId);
        Room room = roomService.findById(roomId);

        if (checkout.isBefore(checking)){
            throw new RuntimeException("Checkout cannot be before checking");
        }

        long days = ChronoUnit.DAYS.between(checking.toLocalDate(), checkout.toLocalDate());
        if (days <= 0){
            throw new IllegalArgumentException("Reservation must be at least 1 day");
        }

        BigDecimal totalPrice = room.getPricePerDay().multiply(BigDecimal.valueOf(days));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setCheckingDate(checking);
        reservation.setCheckoutDate(checkout);
        reservation.setTotalValue(totalPrice);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        return reservationRepository.save(reservation);
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
