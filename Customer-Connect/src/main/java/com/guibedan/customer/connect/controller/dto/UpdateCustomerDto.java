package com.guibedan.customer.connect.controller.dto;

import com.guibedan.customer.connect.validation.PhoneValidation;
import jakarta.validation.constraints.Email;

public record UpdateCustomerDto(
        @Email String email,
        @PhoneValidation String phone
) {
}
