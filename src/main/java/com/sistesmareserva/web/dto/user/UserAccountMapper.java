package com.sistesmareserva.web.dto.user;

import com.sistesmareserva.model.UserAccount;

public class UserAccountMapper {

    public static ResponseUserAccountDTO toDTO(UserAccount user){
       return new ResponseUserAccountDTO(
                user.getEmail()
        );
    }
}
