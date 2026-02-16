package com.sistesmareserva.web.dto.room;

import java.math.BigDecimal;

public record UpdateRoomPriceDTO(
        BigDecimal updatePricePerDay
)
{ }
