package com.sistesmareserva.web.controller;

import com.sistesmareserva.web.dto.client.ClientResponseDTO;
import com.sistesmareserva.web.dto.client.CreateClientDTO;
import com.sistesmareserva.model.Client;
import com.sistesmareserva.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody CreateClientDTO dto){
        return ResponseEntity.status(201).body(clientService.create(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findByIdDto(@PathVariable Long id){
        return ResponseEntity.ok().body(clientService.findByIdDto(id));
    }

}
