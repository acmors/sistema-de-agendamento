package com.sistesmareserva.controller;

import com.sistesmareserva.dto.room.CreateRoomDTO;
import com.sistesmareserva.dto.room.ResponseRoomDTO;
import com.sistesmareserva.dto.room.UpdateRoomPriceDTO;
import com.sistesmareserva.dto.room.UpdateRoomStatusDTO;
import com.sistesmareserva.model.Room;
import com.sistesmareserva.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<ResponseRoomDTO> create(@RequestBody CreateRoomDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(dto));
    }

   @GetMapping
   public ResponseEntity<List<ResponseRoomDTO>> getAllRooms(){
        return ResponseEntity.ok(roomService.listRooms());
   }

   @GetMapping("/{id}")
    public ResponseEntity<ResponseRoomDTO> findByIdDto(@PathVariable Long id){
        return ResponseEntity.ok(roomService.findByIdDto(id));
   }

   @PutMapping("/{id}")
    public ResponseEntity<ResponseRoomDTO> updateRoomPrice(@PathVariable Long id, @RequestBody UpdateRoomPriceDTO room){
        return ResponseEntity.ok(roomService.updateRoomPrice(id, room));
   }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRoomDTO> updateRoomStatus(@PathVariable Long id, @RequestBody UpdateRoomStatusDTO dto){
        return ResponseEntity.ok(roomService.updateRoomStatus(id, dto));
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
   }
}
