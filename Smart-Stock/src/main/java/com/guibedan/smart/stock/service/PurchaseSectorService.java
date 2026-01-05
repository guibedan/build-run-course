package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSectorService {

    private final AuthService authService;

    public PurchaseSectorService(AuthService authService) {
        this.authService = authService;
    }

    public boolean sendPurchaseRequest(CsvStockItem stockItem, Integer purchaseQuantity) {

        var token = authService.getToken();

        return false;
    }

}
