package com.sistesmareserva.service;

import com.sistesmareserva.model.UserAccount;
import com.sistesmareserva.model.enums.Role;
import com.sistesmareserva.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userRepository;


    public UserAccount create(String email, String password ){
        try {
            UserAccount user = new UserAccount();
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(Role.USER);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao criar um novo usuario");
        }
    }
}
