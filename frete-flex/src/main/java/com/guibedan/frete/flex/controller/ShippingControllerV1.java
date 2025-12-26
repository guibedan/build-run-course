package com.guibedan.frete.flex.controller;

import com.guibedan.frete.flex.controller.dto.ShippingResponse;
import com.guibedan.frete.flex.domain.enums.ShippingType;
import com.guibedan.frete.flex.service.ShippingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/shipping")
public class ShippingControllerV1 {

    private final ShippingService shippingService;

    public ShippingControllerV1(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<ShippingResponse> calculateCost(@RequestParam("type") ShippingType shippingType,
                                                            @RequestParam("distance") Double distance,
                                                            @RequestParam("weight") Double weight) {
        Double shippingCost = shippingService.calculateCost(shippingType, distance, weight);
        return ResponseEntity.ok(new ShippingResponse(shippingCost));
    }

}
