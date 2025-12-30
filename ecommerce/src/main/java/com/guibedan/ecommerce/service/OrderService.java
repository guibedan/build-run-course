package com.guibedan.ecommerce.service;

import com.guibedan.ecommerce.controller.dto.CreateOrderDto;
import com.guibedan.ecommerce.controller.dto.OrderItemDto;
import com.guibedan.ecommerce.controller.dto.OrderResponseDto;
import com.guibedan.ecommerce.controller.dto.OrderSummaryDto;
import com.guibedan.ecommerce.entity.Order;
import com.guibedan.ecommerce.entity.OrderItem;
import com.guibedan.ecommerce.entity.Product;
import com.guibedan.ecommerce.entity.pk.OrderItemPK;
import com.guibedan.ecommerce.exception.CreateOrderException;
import com.guibedan.ecommerce.repository.OrderRepository;
import com.guibedan.ecommerce.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(UserService userService, OrderRepository orderRepository, ProductRepository productRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // create order
    public Long createOrder(@Valid CreateOrderDto createOrderDto) {
        var order = new Order();

        var user = userService.getUserById(createOrderDto.userId());
        var orderItems = validateOrderItems(order, createOrderDto.items());

        var total = calculateOrderTotal(orderItems);

        order.setOrderDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalValue(total);

        return orderRepository.save(order).getId();
    }

    private List<OrderItem> validateOrderItems(Order order, List<OrderItemDto> items) {
        if (items == null || items.isEmpty())
            throw new CreateOrderException("order items cannot be empty");

        return items.stream().map(orderItemDto -> getOrderItem(order, orderItemDto)).collect(Collectors.toList());
    }

    private OrderItem getOrderItem(Order order, OrderItemDto orderItemDto) {
        var orderItem = new OrderItem();
        var id = new OrderItemPK();

        var product = getProduct(orderItemDto.productId());
        id.setProduct(product);
        id.setOrder(order);

        orderItem.setId(id);
        orderItem.setQuantity(orderItemDto.quantity());
        orderItem.setSalePrice(product.getPrice());

        return orderItem;
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
    }

    private BigDecimal calculateOrderTotal(List<OrderItem> items) {
        return items.stream().map(i -> i.getSalePrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    // list orders
    public Page<OrderSummaryDto> listOrders(Integer page, Integer pageSize, String orderBy, UUID userId) {
        PageRequest pageRequest = getPageRequest(page, pageSize, orderBy);
        return findWithFilter(userId, pageRequest);
    }

    private PageRequest getPageRequest(int page, int pageSize, String orderBy) {
        var direction = orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, pageSize, direction, "orderDate");
    }

    private Page<OrderSummaryDto> findWithFilter(UUID userId, PageRequest pageRequest) {
        if (userId != null) {
            var user = userService.getUserById(userId);
            return orderRepository.findByUser(user, pageRequest).map(OrderSummaryDto::fromEntity);
        }

        return orderRepository.findAll(pageRequest).map(OrderSummaryDto::fromEntity);
    }

    // get order
    public OrderResponseDto getOrderById(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));
        return OrderResponseDto.fromEntity(order);
    }

}
