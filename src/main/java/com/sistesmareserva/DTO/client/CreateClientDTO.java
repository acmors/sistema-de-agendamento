package com.sistesmareserva.DTO.client;

public record CreateClientDTO(
    String name,
    String cpf,
    String email,
    String password
)
{}
