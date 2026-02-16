package com.sistesmareserva.web.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sistesmareserva.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ResponseReservationDTO(
       String clientName,
       int roomNumber,
       @JsonFormat(pattern = "dd/MM/yyyy")
       LocalDate checkingDate,
       @JsonFormat(pattern = "dd/MM/yyyy")
       LocalDate checkoutDate,
       BigDecimal totalValue,
       ReservationStatus status
) {
}
