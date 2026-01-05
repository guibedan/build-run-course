package com.guibedan.smart.stock.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvStockItem {

    @CsvBindByName(column = "item_id")
    private String itemId;

    @CsvBindByName(column = "item_name")
    private String itemName;

    @CsvBindByName(column = "quantity")
    private Integer quantity;

    @CsvBindByName(column = "reorder_threshold")
    private Integer reorderThreshold;

    @CsvBindByName(column = "supplier_name")
    private String supplierName;

    @CsvBindByName(column = "supplier_email")
    private String supplierEmail;

    @CsvBindByName(column = "last_stock_update_time")
    private String lastStockUpdateTime;

}
