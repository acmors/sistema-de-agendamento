package com.sistesmareserva.controller;

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
    public ResponseEntity<Client> create(@RequestBody Client client){
        clientService.create(client, client.getPassword());
        return ResponseEntity.status(201).body(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>> listAllClients(){
        return ResponseEntity.ok(clientService.listAllClients());
    }
}
