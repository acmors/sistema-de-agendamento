package com.sistesmareserva.service;

import com.sistesmareserva.dto.room.*;
import com.sistesmareserva.model.Room;
import com.sistesmareserva.model.enums.Status;
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
    public ResponseRoomDTO create(CreateRoomDTO dto){

        if (roomRepository.existsByNumber(dto.number())) throw new IllegalArgumentException("Already exists a room with this number");

        Room room = new Room();
        room.setNumber(dto.number());
        room.setType(dto.type());
        room.setPricePerDay(dto.pricePerDay());
        room.setStatus(Status.AVAILABLE);
        
        Room save = roomRepository.save(room);
        return RoomMapper.toDTO(save);
    }

    @Transactional(readOnly = true)
    public Room findById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }
    @Transactional(readOnly = true)
    public ResponseRoomDTO findByIdDto(Long id){
        Room room = findById(id);
        return RoomMapper.toDTO(room);
    }

    @Transactional(readOnly = true)
    public List<ResponseRoomDTO> listRooms(){

        return roomRepository.findAll()
                .stream()
                .map(RoomMapper::toDTO)
                .toList();
    }

    @Transactional
    public ResponseRoomDTO updateRoomPrice(Long id, UpdateRoomPriceDTO dto){
        Room room = findById(id);
        room.setPricePerDay(dto.updatePricePerDay());
        roomRepository.save(room);

        return RoomMapper.toDTO(room);
    }

    @Transactional
    public ResponseRoomDTO updateRoomStatus(Long id, UpdateRoomStatusDTO dto){
        Room room = findById(id);
        room.setStatus(dto.status());
        roomRepository.save(room);

        return RoomMapper.toDTO(room);
    }

    @Transactional
    public void deleteById(Long id){
        Room room = findById(id);
        roomRepository.deleteById(id);
    }
}
