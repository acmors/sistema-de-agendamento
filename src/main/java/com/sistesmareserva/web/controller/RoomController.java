package com.sistesmareserva.web.controller;

import com.sistesmareserva.web.dto.room.CreateRoomDTO;
import com.sistesmareserva.web.dto.room.ResponseRoomDTO;
import com.sistesmareserva.web.dto.room.UpdateRoomPriceDTO;
import com.sistesmareserva.web.dto.room.UpdateRoomStatusDTO;
import com.sistesmareserva.service.RoomService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponseRoomDTO> create(@Valid @RequestBody CreateRoomDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(dto));
    }

   @GetMapping
   public ResponseEntity<List<ResponseRoomDTO>> getAllRooms(){
        return ResponseEntity.ok(roomService.listRooms());
   }

   @GetMapping("/{number}")
    public ResponseEntity<ResponseRoomDTO> findByNumber(@PathVariable int number){
        return ResponseEntity.ok(roomService.findByRoomNumberDto(number));
   }

   @PutMapping("/{id}/price")
    public ResponseEntity<ResponseRoomDTO> updateRoomPrice(@PathVariable Long id, @RequestBody UpdateRoomPriceDTO room){
        return ResponseEntity.ok(roomService.updateRoomPrice(id, room));
   }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseRoomDTO> updateRoomStatus(@PathVariable Long id, @RequestBody UpdateRoomStatusDTO dto){
        return ResponseEntity.ok(roomService.updateRoomStatus(id, dto));
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
   }
}
