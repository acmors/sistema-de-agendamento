package com.sistesmareserva.web.dto.reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateReservationDTO(

            String cpf,
            int roomNumber,

            @JsonFormat(pattern = "dd/MM/yyyy")
            @NotNull
            LocalDate checking,

            @JsonFormat(pattern = "dd/MM/yyyy")
            @NotNull
            LocalDate checkout
)
{ }
