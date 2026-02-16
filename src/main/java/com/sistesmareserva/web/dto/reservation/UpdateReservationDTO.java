package com.sistesmareserva.web.dto.reservation;

import com.sistesmareserva.model.enums.ReservationStatus;

import java.time.LocalDate;

public record UpdateReservationDTO(
        LocalDate checkout,
        ReservationStatus status
) {
}
