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

    public UserAccount updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword){
        if(!newPassword.equals(confirmPassword)){
            throw new RuntimeException("new password dont match with password confirmation");
        }

        UserAccount user = findById(id);
        if(!currentPassword.equals(user.getPassword())){
            throw new RuntimeException("Current password is wrong.");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }


    public UserAccount findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id not found"));
    }
}
