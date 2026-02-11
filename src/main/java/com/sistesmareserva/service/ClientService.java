package com.sistesmareserva.service;

import com.sistesmareserva.model.Client;
import com.sistesmareserva.model.UserAccount;
import com.sistesmareserva.model.enums.Role;
import com.sistesmareserva.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    private final UserAccountService userService;


    @Transactional
    public Client create(String email, String password, String name,String cpf){
        UserAccount user = userService.create(email, password);
        Client client = new Client();
        client.setUser(user);
        client.setName(name);
        client.setCpf(cpf);
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client findById(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clint not found."));
    }


}
