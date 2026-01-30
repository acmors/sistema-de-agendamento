package com.sistesmareserva.repository;

import com.sistesmareserva.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
