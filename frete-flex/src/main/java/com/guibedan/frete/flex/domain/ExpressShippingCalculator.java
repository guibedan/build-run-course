package com.guibedan.frete.flex.domain;

import org.springframework.stereotype.Component;

@Component
public class ExpressShippingCalculator implements ShippingCalculator {

    @Override
    public Double calculate(Double distance, Double weight) {
        return weight * 1.5 + distance * 0.75;
    }

}
