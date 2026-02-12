package com.sistesmareserva.service;

import com.sistesmareserva.dto.reservation.CreateReservationDTO;
import com.sistesmareserva.dto.reservation.ReservationMapper;
import com.sistesmareserva.dto.reservation.ResponseReservationDTO;
import com.sistesmareserva.dto.reservation.UpdateReservationDTO;
import com.sistesmareserva.model.Client;
import com.sistesmareserva.model.Reservation;
import com.sistesmareserva.model.Room;
import com.sistesmareserva.model.enums.ReservationStatus;
import com.sistesmareserva.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientService clientService;
    private final RoomService roomService;

    @Transactional
    public ResponseReservationDTO create(CreateReservationDTO dto){

        Client client = clientService.findById(dto.clientId());
        Room room = roomService.findById(dto.roomId());

        long days = ChronoUnit.DAYS.between(dto.checking(), dto.checkout());
        if (days <= 0) throw new IllegalArgumentException("Reservation must be at least 1 day");

        BigDecimal totalPrice = room.getPricePerDay().multiply(BigDecimal.valueOf(days));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setCheckingDate(dto.checking());
        reservation.setCheckoutDate(dto.checkout());
        reservation.setTotalValue(totalPrice);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);

        return ReservationMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public Reservation findById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("entity not found"));
    }

    @Transactional(readOnly = true)
    public ResponseReservationDTO findByIdDto(Long id){
        Reservation reservation = findById(id);

        return ReservationMapper.toDTO(reservation);
    }

    @Transactional(readOnly = true)
    public List<ResponseReservationDTO> findAll(){
       return reservationRepository.findAll()
               .stream()
               .map(ReservationMapper::toDTO)
               .toList();
    }

    @Transactional
    public ResponseReservationDTO update(Long id, UpdateReservationDTO dto){
        Reservation reservation = findById(id);

        if (dto.checkout() != null) reservation.setCheckoutDate(dto.checkout());
        if(dto.status() != null) reservation.setReservationStatus(dto.status());

        Reservation update = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(update);
    }

    @Transactional
    public void cancelReservation(Long reservationId){
        Reservation reservation = findById(reservationId);

        if (reservation.getReservationStatus() == ReservationStatus.CANCELED){
            throw new IllegalArgumentException("Reservation already cancelled");
        }

        reservation.setReservationStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteById(Long id){
        Reservation reservation = findById(id);
        reservationRepository.deleteById(id);
    }

}
