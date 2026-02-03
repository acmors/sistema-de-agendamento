package com.sistesmareserva.service;

import com.sistesmareserva.model.Room;
import com.sistesmareserva.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;


    @Transactional
    public Room create(Room room){
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Room findById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public List<Room> listRooms(){
        return roomRepository.findAll();
    }

    @Transactional
    public Room updateRoom(Long id, Room newRoom){
        Room room = findById(id);

        room.setNumber(newRoom.getNumber());
        room.setType(newRoom.getType());
        room.setPricePerDay(newRoom.getPricePerDay());
        room.setStatus(newRoom.getStatus());
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteById(Long id){
        roomRepository.deleteById(id);
    }
}
