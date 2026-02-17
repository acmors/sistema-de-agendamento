package com.sistesmareserva.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordDTO(

         @NotBlank
         @Size(min = 8, max = 20)
         String currentPassword,

         @NotBlank
         @Size(min = 8, max = 20)
         String newPassword,

         @NotBlank
         @Size(min = 8, max = 20)
         String confirmPassword

) {
}
