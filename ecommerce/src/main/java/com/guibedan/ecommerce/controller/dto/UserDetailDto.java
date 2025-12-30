package com.guibedan.ecommerce.controller.dto;

import com.guibedan.ecommerce.entity.User;

public record UserDetailDto(
        String fullName,
        String address,
        String number,
        String complement
) {

    public static UserDetailDto fromEntity(User user) {
        var billingAddress = user.getBillingAddress();
        return new UserDetailDto(user.getFullName(), billingAddress.getAddress(), billingAddress.getNumber(), billingAddress.getComplement());
    }

}
