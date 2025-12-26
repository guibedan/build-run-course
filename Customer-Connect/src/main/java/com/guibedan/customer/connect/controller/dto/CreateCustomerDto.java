package com.guibedan.customer.connect.controller.dto;

import com.guibedan.customer.connect.validation.PhoneValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateCustomerDto(
        @NotBlank String fullName,
        @NotBlank @CPF String cpf,
        @NotBlank @Email String email,
        @NotBlank @PhoneValidation String phone
) {
}
