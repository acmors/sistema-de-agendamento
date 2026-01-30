package com.sistesmareserva.service;

import com.sistesmareserva.model.Client;
import com.sistesmareserva.model.enums.Role;
import com.sistesmareserva.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserAccountService userService;


    @Transactional
    public Client create(Client client, String password){
        userService.prepareAccount(client, password);

        client.setRole(Role.USER);
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public List<Client> listAllClients(){
        return clientRepository.findAll();
    }
}
