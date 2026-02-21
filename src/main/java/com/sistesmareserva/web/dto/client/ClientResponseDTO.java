package com.sistesmareserva.web.dto.client;

public record ClientResponseDTO (
        Long id,
        String name,
        String email,
        String cpf
){

}
