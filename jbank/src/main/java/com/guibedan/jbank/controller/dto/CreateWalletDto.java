package com.guibedan.jbank.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateWalletDto(
        @NotBlank @CPF String cpf,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 3, max = 255) String name
) {
}
