package com.sistesmareserva.web.dto.client;

public record CreateClientDTO(
    String name,
    String cpf,
    String email,
    String password
)
{}
