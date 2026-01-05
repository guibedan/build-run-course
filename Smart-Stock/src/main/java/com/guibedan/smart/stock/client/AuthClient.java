package com.guibedan.smart.stock.client;

import com.guibedan.smart.stock.client.dto.AuthRequest;
import com.guibedan.smart.stock.client.dto.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "AuthClient", url = "${api.auth-url}")
public interface AuthClient {

    @PostMapping("/api/token")
    ResponseEntity<AuthResponse> authentication(AuthRequest authRequest);

}
