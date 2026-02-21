package com.sistesmareserva.service;

import com.sistesmareserva.exception.EntityNotFoundException;
import com.sistesmareserva.exception.UsernameUniqueViolationException;
import com.sistesmareserva.model.Client;
import com.sistesmareserva.model.UserAccount;
import com.sistesmareserva.model.enums.Role;
import com.sistesmareserva.repository.ClientRepository;
import com.sistesmareserva.web.dto.client.ClientMapper;
import com.sistesmareserva.web.dto.client.ClientResponseDTO;
import com.sistesmareserva.web.dto.client.CreateClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserAccountService userService;


    @Transactional
    public ClientResponseDTO create(CreateClientDTO dto){
        try {
            UserAccount user = userService.create(dto.email(), dto.password());
            Client client = new Client();
            client.setUser(user);
            client.setName(dto.name());
            client.setCpf(dto.cpf());
            Client saved = clientRepository.save(client);

            return ClientMapper.toDTO(saved);
        } catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException("Username already exists");
        }
    }

    @Transactional(readOnly = true)
    public Client findById(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found."));
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findByIdDto(Long id){
       Client client = findById(id);

       return ClientMapper.toDTO(client);
    }


    @Transactional(readOnly = true)
    public ClientResponseDTO findByCpfDTO(String cpf){
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("CPF not found."));
        return ClientMapper.toDTO(client);

    }

    @Transactional(readOnly = true)
    public Client findByCpf(String cpf){
        return clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("CPF not found."));

    }


}
