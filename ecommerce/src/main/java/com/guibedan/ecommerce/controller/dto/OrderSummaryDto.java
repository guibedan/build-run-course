package com.guibedan.ecommerce.controller.dto;

import com.guibedan.ecommerce.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSummaryDto(
        Long orderId,
        LocalDateTime orderDate,
        UUID userId,
        BigDecimal total
) {

    public static OrderSummaryDto fromEntity(Order order) {
        return new OrderSummaryDto(order.getId(), order.getOrderDate(), order.getUser().getId(), order.getTotalValue());
    }

}
