package com.guibedan.smart.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Document(collation = "col_purchase_requests")
public class PurchaseRequestEntity {

    @MongoId
    @Field(name = "item_id")
    private String itemId;

    @Field(name = "item_name")
    private String itemName;

    @Field(name = "quantity_on_stock")
    private Integer quantityOnStock;

    @Field(name = "reorder_threshold")
    private Integer reorderThreshold;

    @Field(name = "supplier_name")
    private String supplierName;

    @Field(name = "supplier_email")
    private String supplierEmail;

    @Field(name = "last_stock_update_time")
    private LocalDateTime lastStockUpdateTime;

    @Field(name = "purchase_quantity")
    private Integer purchaseQuantity;

    @Field(name = "purchase_with_success")
    private boolean purchaseWithSuccess;

    @Field(name = "purchase_date_time")
    private LocalDateTime purchaseDateTime;

}
