package com.guibedan.ecommerce.controller.dto;

import com.guibedan.ecommerce.entity.Order;
import com.guibedan.ecommerce.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
        Long orderId,
        BigDecimal total,
        LocalDateTime orderDate,
        UUID userId,
        List<OrderItemResponseDto> items
) {

    public static OrderResponseDto fromEntity(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getTotalValue(),
                order.getOrderDate(),
                order.getUser().getId(),
                getItems(order.getOrderItems())
        );
    }

    private static List<OrderItemResponseDto> getItems(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemResponseDto::fromEntity).toList();
    }

}
