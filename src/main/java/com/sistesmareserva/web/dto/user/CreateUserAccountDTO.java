package com.sistesmareserva.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserAccountDTO(
        @NotBlank
        @Email(message = "Formato de Email inválido.", regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @NotBlank
        @Size(min = 8, max = 20)
        String password
) {
}
