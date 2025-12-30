package com.guibedan.ecommerce.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank @Size(min = 3, max = 255) String fullName,
        @NotBlank @Size(min = 3, max = 255) String address,
        @NotBlank String number,
        String complement
) {
}
