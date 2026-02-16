package com.sistesmareserva.web.dto.user;

public record UserPasswordDTO(
         String currentPassword,
         String newPassword,
         String confirmPassword

) {
}
