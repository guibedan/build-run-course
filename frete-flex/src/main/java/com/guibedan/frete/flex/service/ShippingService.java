package com.guibedan.frete.flex.service;

import com.guibedan.frete.flex.domain.ShippingCalculator;
import com.guibedan.frete.flex.domain.enums.ShippingType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShippingService {

    private final Map<String, ShippingCalculator> shippingCalculator;

    public ShippingService(Map<String, ShippingCalculator> shippingCalculator) {
        this.shippingCalculator = shippingCalculator;
    }

    public Double calculateCost(ShippingType shippingType, Double distance, Double weight) {
        return shippingCalculator.get(shippingType.getValue()).calculate(distance, weight);
    }

}
