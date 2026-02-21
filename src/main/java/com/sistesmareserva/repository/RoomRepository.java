package com.sistesmareserva.repository;

import com.sistesmareserva.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByNumber(int number);

    Optional<Room> findByNumber(int number);
}

