package com.sistesmareserva.dto.room;

import com.sistesmareserva.model.enums.Type;

import java.math.BigDecimal;

public record CreateRoomDTO(
    int number,
    Type type,
    BigDecimal pricePerDay
)
{ }
