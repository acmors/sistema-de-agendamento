package com.sistesmareserva.repository;

import com.sistesmareserva.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByNumber(int number);
}

