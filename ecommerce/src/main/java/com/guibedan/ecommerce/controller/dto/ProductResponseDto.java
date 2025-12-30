package com.guibedan.ecommerce.controller.dto;

import com.guibedan.ecommerce.entity.Product;
import com.guibedan.ecommerce.entity.Tag;

import java.util.List;

public record ProductResponseDto(
        Long productId,
        String productName,
        List<TagResponseDto> tags
) {

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getProductName(),
                getTags(product.getTags())
        );
    }

    private static List<TagResponseDto> getTags(List<Tag> tags) {
        return tags.stream().map(TagResponseDto::fromEntity).toList();
    }

}
