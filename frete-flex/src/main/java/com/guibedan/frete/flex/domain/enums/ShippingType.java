package com.guibedan.frete.flex.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShippingType {
    STANDARD("standardShippingCalculator"),
    EXPRESS("expressShippingCalculator");

    private final String value;
}
