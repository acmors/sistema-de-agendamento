package com.sistesmareserva.service;

import com.sistesmareserva.exception.EntityNotFoundException;
import com.sistesmareserva.exception.ResourceAlreadyExistsException;
import com.sistesmareserva.exception.RoomNumberUniqueViolationException;
import com.sistesmareserva.model.Room;
import com.sistesmareserva.model.enums.Status;
import com.sistesmareserva.repository.RoomRepository;
import com.sistesmareserva.web.dto.room.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;


    @Transactional
    public ResponseRoomDTO create(CreateRoomDTO dto){

        try {
            Room room = new Room();
            room.setNumber(dto.number());
            room.setType(dto.type());
            room.setPricePerDay(dto.pricePerDay());
            room.setStatus(Status.AVAILABLE);

            Room save = roomRepository.save(room);
            return RoomMapper.toDTO(save);
        }catch (DataIntegrityViolationException e){
            throw new RoomNumberUniqueViolationException("Number room already exists.");
        }
    }

    @Transactional(readOnly = true)
    public Room findById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Transactional(readOnly = true)
    public Room findByRoomNumber(int roomNumber){
        return roomRepository.findByNumber(roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Room number not found."));

    }

    @Transactional(readOnly = true)
    public ResponseRoomDTO findByRoomNumberDto(int roomNumber){
        Room room = findByRoomNumber(roomNumber);
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
