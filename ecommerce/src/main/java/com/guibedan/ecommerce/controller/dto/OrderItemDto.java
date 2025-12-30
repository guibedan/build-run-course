package com.guibedan.ecommerce.controller.dto;

public record OrderItemDto(
        Integer quantity,
        Long productId
) {
}
