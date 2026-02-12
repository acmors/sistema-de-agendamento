package com.sistesmareserva.dto.room;

import com.sistesmareserva.model.enums.Status;
import com.sistesmareserva.model.enums.Type;

import java.math.BigDecimal;

public record ResponseRoomDTO(
        int number,
        Type type,
        BigDecimal pricePerDay,
        Status status
)
{ }
