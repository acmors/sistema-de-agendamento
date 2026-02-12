package com.sistesmareserva.controller;

import com.sistesmareserva.dto.reservation.CreateReservationDTO;
import com.sistesmareserva.dto.reservation.ResponseReservationDTO;
import com.sistesmareserva.dto.reservation.UpdateReservationDTO;
import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseReservationDTO> create(@RequestBody CreateReservationDTO reservation){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(reservation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseReservationDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.findByIdDto(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservationDTO>> findAll(){

        return ResponseEntity.ok(reservationService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseReservationDTO> update(@PathVariable Long id, @RequestBody UpdateReservationDTO reservation){
        return ResponseEntity.ok(reservationService.update(id, reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id){
        reservationService.cancelReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
