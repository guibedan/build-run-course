package com.guibedan.ecommerce.controller.dto;

import com.guibedan.ecommerce.entity.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        BigDecimal salePrice,
        Integer quantity,
        ProductResponseDto product
) {

    public static OrderItemResponseDto fromEntity(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getSalePrice(),
                orderItem.getQuantity(),
                ProductResponseDto.fromEntity(orderItem.getId().getProduct())
        );
    }

}
