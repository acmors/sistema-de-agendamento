package com.sistesmareserva.dto.client;

public record CreateClientDTO(
    String name,
    String cpf,
    String email,
    String password
)
{}
