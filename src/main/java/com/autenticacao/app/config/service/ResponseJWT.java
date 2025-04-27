package com.autenticacao.app.config.service;

import lombok.Getter;

@Getter
public class ResponseJWT {
    private String accessToken;
    private String refreshToken;

    public ResponseJWT(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
