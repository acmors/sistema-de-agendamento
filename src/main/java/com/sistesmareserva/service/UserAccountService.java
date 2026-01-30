package com.sistesmareserva.service;

import com.sistesmareserva.model.UserAccount;
import com.sistesmareserva.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userRepository;

    protected void prepareAccount(UserAccount user, String password){
        user.setPassword(password);
    }
}
