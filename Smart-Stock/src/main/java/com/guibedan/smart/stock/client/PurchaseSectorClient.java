package com.guibedan.smart.stock.client;

import com.guibedan.smart.stock.client.dto.PurchaseRequest;
import com.guibedan.smart.stock.client.dto.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "PurchaseSectorClient", url = "${api.purchase-sector-url}")
public interface PurchaseSectorClient {

    @PostMapping("/api/purchases")
    ResponseEntity<PurchaseResponse> sendPurchaseRequest(@RequestHeader("Authorization") String token,
                                                         @RequestBody PurchaseRequest purchaseRequest);

}
