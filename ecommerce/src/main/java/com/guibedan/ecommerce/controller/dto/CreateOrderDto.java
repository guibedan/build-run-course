package com.guibedan.ecommerce.controller.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record CreateOrderDto(
        UUID userId,
        @NotEmpty List<OrderItemDto> items
) {
}
