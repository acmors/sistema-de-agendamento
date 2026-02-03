package com.sistesmareserva.DTO.user;

public record UserPasswordDTO(
         String currentPassword,
         String newPassword,
         String confirmPassword

) {
}
