package com.autenticacao.app.config.service;

import lombok.Getter;

@Getter
public class ResponseJWT {
    private String acessToken;
    private String refreshToken;

    public ResponseJWT(String acessToken, String refreshToken) {
        this.acessToken = acessToken;
        this.refreshToken = refreshToken;
    }
}
