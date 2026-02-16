package com.sistesmareserva.web.dto.room;

import com.sistesmareserva.model.enums.Status;

public record UpdateRoomStatusDTO(
        Status status
)
{ }
