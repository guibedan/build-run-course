package com.guibedan.ecommerce.controller.dto;

import com.guibedan.ecommerce.entity.Tag;

public record TagResponseDto(
        Long tagId,
        String name
) {

    public static TagResponseDto fromEntity(Tag tag) {
        return new TagResponseDto(tag.getId(), tag.getName());
    }

}
