package com.guibedan.ecommerce.entity.pk;

import com.guibedan.ecommerce.entity.Order;
import com.guibedan.ecommerce.entity.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class OrderItemPK {

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

}
