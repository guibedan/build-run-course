package com.guibedan.demojpa.controller.dto;

import com.guibedan.demojpa.entity.UserEntity;

import java.time.Instant;

public record UserDetailDto(String username, int age, Instant createdAt) {
    public static UserDetailDto fromEntity(UserEntity userEntity) {
        return new UserDetailDto(userEntity.getUsername(), userEntity.getAge(), userEntity.getCreatedAt());
    }
}
