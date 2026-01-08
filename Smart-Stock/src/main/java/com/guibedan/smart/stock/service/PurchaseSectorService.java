package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.client.PurchaseSectorClient;
import com.guibedan.smart.stock.client.dto.PurchaseRequest;
import com.guibedan.smart.stock.domain.CsvStockItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PurchaseSectorService {

    private final AuthService authService;
    private final PurchaseSectorClient purchaseSectorClient;

    public PurchaseSectorService(AuthService authService, PurchaseSectorClient purchaseSectorClient) {
        this.purchaseSectorClient = purchaseSectorClient;
        this.authService = authService;
    }

    public boolean sendPurchaseRequest(CsvStockItem stockItem, Integer purchaseQuantity) {

        var token = authService.getToken();

        var request = new PurchaseRequest(
                stockItem.getItemId(),
                stockItem.getItemName(),
                stockItem.getSupplierName(),
                stockItem.getSupplierEmail(),
                purchaseQuantity
        );

        var response = purchaseSectorClient.sendPurchaseRequest(token, request);

        if (response.getStatusCode().value() != HttpStatus.ACCEPTED.value()) {
            log.error("error while sending purchase request, status: {}, response: {}",
                    response.getStatusCode(), response.getBody());
            return false;
        }

        return false;
    }

}
