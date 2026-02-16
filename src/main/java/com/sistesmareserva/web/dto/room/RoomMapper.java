package com.sistesmareserva.web.dto.room;

import com.sistesmareserva.model.Room;

public class RoomMapper {

    public static ResponseRoomDTO toDTO(Room room){
        return new ResponseRoomDTO(
                room.getNumber(),
                room.getType(),
                room.getPricePerDay(),
                room.getStatus()
        );
    }
}
