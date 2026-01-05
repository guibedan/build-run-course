package com.guibedan.smart.stock.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {

    @Value("${app.client-id}")
    private String clientId;

    @Value("${app.client-secret}")
    private String clientSecret;

}
