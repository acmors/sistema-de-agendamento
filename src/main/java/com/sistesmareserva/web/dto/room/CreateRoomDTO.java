package com.sistesmareserva.web.dto.room;

import com.sistesmareserva.model.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRoomDTO(

        @NotNull
        Integer number,

        @NotNull
        Type type,

        @NotNull
        BigDecimal pricePerDay
)
{ }
