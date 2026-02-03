package com.sistesmareserva.controller;

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
    public ResponseEntity<Room> create(@RequestBody Room room){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(room));
    }

   @GetMapping
   public ResponseEntity<List<Room>> getAllRooms(){
        return ResponseEntity.ok(roomService.listRooms());
   }

   @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.findById(id));
   }

   @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room room){
        return ResponseEntity.ok(roomService.updateRoom(id, room));
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
   }
}
