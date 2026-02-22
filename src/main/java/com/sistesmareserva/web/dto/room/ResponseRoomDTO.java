package com.sistesmareserva.web.dto.room;

import com.sistesmareserva.model.enums.Status;
import com.sistesmareserva.model.enums.Type;

import java.math.BigDecimal;

public record ResponseRoomDTO(

        Long id,
        int number,
        Type type,
        BigDecimal pricePerDay,
        Status status
)
{ }
