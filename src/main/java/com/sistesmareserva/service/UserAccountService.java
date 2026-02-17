package com.sistesmareserva.service;

import com.sistesmareserva.exception.ConflictException;
import com.sistesmareserva.exception.EntityNotFoundException;
import com.sistesmareserva.exception.PasswordInvalidException;
import com.sistesmareserva.exception.UsernameUniqueViolationException;
import com.sistesmareserva.model.UserAccount;
import com.sistesmareserva.model.enums.Role;
import com.sistesmareserva.repository.UserAccountRepository;
import com.sistesmareserva.web.dto.user.CreateUserAccountDTO;
import com.sistesmareserva.web.dto.user.ResponseUserAccountDTO;
import com.sistesmareserva.web.dto.user.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userRepository;

    @Transactional
    public UserAccount create(String email, String password ){
        try {
            UserAccount user = new UserAccount();
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(Role.USER);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueViolationException("Username already exists.");
        }
    }

    @Transactional
    public ResponseUserAccountDTO create(CreateUserAccountDTO dto){
        UserAccount user = create(dto.email(), dto.password());
        return UserAccountMapper.toDTO(user);
    }

    @Transactional
    public UserAccount updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword){
        if(!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("new password dont match with password confirmation");
        }

        UserAccount user = findById(id);
        if(!currentPassword.equals(user.getPassword())){
            throw new PasswordInvalidException("Current password is wrong.");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public UserAccount findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found."));
    }

    @Transactional(readOnly = true)
    public ResponseUserAccountDTO findByIdDto(Long id){
        UserAccount user = findById(id);
        return UserAccountMapper.toDTO(user);
    }
}
