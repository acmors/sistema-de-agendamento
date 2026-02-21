package com.sistesmareserva.web.dto.reservation;

import com.sistesmareserva.model.Reservation;

public class ReservationMapper {

    public static ResponseReservationDTO toDTO(Reservation reservation){
        return new ResponseReservationDTO(
                reservation.getClient().getName(),
                reservation.getRoom().getNumber(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getTotalValue(),
                reservation.getReservationStatus()
        );
    }
}
