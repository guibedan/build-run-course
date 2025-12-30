package com.guibedan.ecommerce.controller;

import com.guibedan.ecommerce.controller.dto.*;
import com.guibedan.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrderControllerV1 {

    private final OrderService orderService;

    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        var orderId = orderService.createOrder(createOrderDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<OrderSummaryDto>> listOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                                                                   @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy,
                                                                   @RequestParam(name = "userId", required = false) UUID userId) {
        var pageResponse = orderService.listOrders(page, pageSize, orderBy, userId);
        var response = new ApiResponse<>(pageResponse.getContent(), new PaginationResponse(page, pageSize, pageResponse.getTotalElements(), pageResponse.getTotalPages()));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("orderId") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(orderResponseDto);
    }

}
