package com.sistesmareserva.dto.room;

import java.math.BigDecimal;

public record UpdateRoomPriceDTO(
        BigDecimal updatePricePerDay
)
{ }
