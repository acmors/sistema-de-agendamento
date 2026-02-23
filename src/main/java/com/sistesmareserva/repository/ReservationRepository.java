package com.sistesmareserva.repository;

import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByRoomAndCheckinDateLessThanAndCheckoutDateGreaterThan(
            Room room,
            LocalDate checkout,
            LocalDate checkin
    );

    boolean existsByRoomAndCheckinDateLessThanAndCheckoutDateGreaterThanAndIdNot(
            Room room,
            LocalDate newCheckout,
            LocalDate checkin,
            Long id
    );
}
