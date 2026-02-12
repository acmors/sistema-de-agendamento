package com.sistesmareserva.dto.user;

public record UserPasswordDTO(
         String currentPassword,
         String newPassword,
         String confirmPassword

) {
}
