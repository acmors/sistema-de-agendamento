package com.sistesmareserva.web.dto.client;

import com.sistesmareserva.model.Client;

public class ClientMapper {

    public static ClientResponseDTO toDTO(Client client){
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getUser().getEmail(),
                client.getCpf()
        );
    }
}
