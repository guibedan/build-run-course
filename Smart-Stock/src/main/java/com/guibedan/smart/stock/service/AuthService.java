package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.client.AuthClient;
import com.guibedan.smart.stock.client.dto.AuthRequest;
import com.guibedan.smart.stock.config.AppConfig;
import com.guibedan.smart.stock.exception.SmartStockException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private static final String GRANT_TYPE = "client_credentials";

    private static String token;
    private static LocalDateTime expiresIn;

    private final AuthClient authClient;
    private final AppConfig appConfig;

    public AuthService(AuthClient authClient, AppConfig appConfig) {
        this.authClient = authClient;
        this.appConfig = appConfig;
    }

    public String getToken() {
        if (token == null) {
            generateToken();
        } else if (expiresIn.isBefore(LocalDateTime.now())) {
            generateToken();
        }

        return token;
    }

    private void generateToken() {
        var request = new AuthRequest(
                GRANT_TYPE,
                appConfig.getClientId(),
                appConfig.getClientSecret()
        );

        var response = authClient.authentication(request);

        if (!response.getStatusCode().is2xxSuccessful())
            throw new SmartStockException("cannot generate token, status: " + response.getStatusCode() + ", response: "
                    + response.getBody());

        token = response.getBody().accessToken();
        expiresIn = LocalDateTime.now().plusSeconds(response.getBody().expiresIn());
    }

}
