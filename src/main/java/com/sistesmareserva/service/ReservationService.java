package com.sistesmareserva.service;

import com.sistesmareserva.exception.ConflictException;
import com.sistesmareserva.exception.EntityNotFoundException;
import com.sistesmareserva.exception.StatusAlreadyExistsException;
import com.sistesmareserva.model.enums.Status;
import com.sistesmareserva.web.dto.reservation.CreateReservationDTO;
import com.sistesmareserva.web.dto.reservation.ReservationMapper;
import com.sistesmareserva.web.dto.reservation.ResponseReservationDTO;
import com.sistesmareserva.web.dto.reservation.UpdateReservationDTO;
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

        Client client = clientService.findByCpf(dto.cpf());
        Room room = roomService.findByRoomNumber(dto.roomNumber());

        if (room.getStatus() == Status.RESERVED) throw new ConflictException("Room already reserved.");
        if (dto.checkin().isBefore(LocalDate.now())) throw new ConflictException("Checkin cannot be in the past");
        if (!dto.checkout().isAfter(dto.checkin())) throw new ConflictException("Checkout must be after checkin");

        boolean roomIsAlreadyReserved =
                reservationRepository.existsByRoomAndCheckinDateLessThanAndCheckoutDateGreaterThan(
                        room,
                        dto.checkout(),
                        dto.checkin()
                );

        if (roomIsAlreadyReserved) throw new ConflictException("Room is already reserved in this period.");

        long days = ChronoUnit.DAYS.between(dto.checkin(), dto.checkout());
        BigDecimal totalPrice = room.getPricePerDay().multiply(BigDecimal.valueOf(days));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setCheckinDate(dto.checkin());
        reservation.setCheckoutDate(dto.checkout());
        reservation.setTotalValue(totalPrice);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public Reservation findById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));
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

        if (reservation.getReservationStatus() == ReservationStatus.CANCELED) throw new ConflictException("Cannot update a canceled reservation");

        if (dto.checkout() != null) {

            LocalDate newCheckout = dto.checkout();
            LocalDate checkin = reservation.getCheckinDate();
            if (!newCheckout.isAfter(checkin)) throw new ConflictException("Checkout must be after check-in");
            boolean conflict =
                    reservationRepository
                            .existsByRoomAndCheckinDateLessThanAndCheckoutDateGreaterThanAndIdNot(
                                    reservation.getRoom(),
                                    newCheckout,
                                    checkin,
                                    reservation.getId()
                            );

            if (conflict) throw new ConflictException("Room is already reserved in this period");

            reservation.setCheckoutDate(newCheckout);
            long days = ChronoUnit.DAYS.between(checkin, newCheckout);
            BigDecimal totalPrice =
                    reservation.getRoom()
                            .getPricePerDay()
                            .multiply(BigDecimal.valueOf(days));

            reservation.setTotalValue(totalPrice);
        }
        if (dto.status() != null) reservation.setReservationStatus(dto.status());

        Reservation updated = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(updated);
    }

    @Transactional
    public void cancelReservation(Long reservationId){
        Reservation reservation = findById(reservationId);

        if (reservation.getReservationStatus() == ReservationStatus.CANCELED){
            throw new StatusAlreadyExistsException("Reservation is already cancelled");
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
