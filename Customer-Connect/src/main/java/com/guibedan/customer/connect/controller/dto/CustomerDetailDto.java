package com.guibedan.customer.connect.controller.dto;

import com.guibedan.customer.connect.entity.CustomerEntity;

public record CustomerDetailDto(
        String fullName,
        String cpf,
        String email,
        String phone
) {

    public static CustomerDetailDto fromEntity(CustomerEntity entity) {
        return new CustomerDetailDto(entity.getFullName(), entity.getCpf(), entity.getEmail(), entity.getPhone());
    }

}
