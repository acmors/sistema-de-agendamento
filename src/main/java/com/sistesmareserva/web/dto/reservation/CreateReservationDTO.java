package com.sistesmareserva.web.dto.reservation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateReservationDTO(
            Long clientId,
            Long roomId,
            @JsonFormat(pattern = "dd/MM/yyyy")
            LocalDate checking,
            @JsonFormat(pattern = "dd/MM/yyyy")
            LocalDate checkout
)
{ }
