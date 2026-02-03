package com.sistesmareserva.controller;

import com.sistesmareserva.DTO.client.CreateClientDTO;
import com.sistesmareserva.model.Client;
import com.sistesmareserva.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody CreateClientDTO dto){
        Client client = clientService.create(
                dto.email(),
                dto.password(),
                dto.name(),
                dto.cpf()
        );

        return ResponseEntity.status(201).body(client);
    }

}
