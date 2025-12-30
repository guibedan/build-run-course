package com.guibedan.ecommerce.repository;

import com.guibedan.ecommerce.entity.OrderItem;
import com.guibedan.ecommerce.entity.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
