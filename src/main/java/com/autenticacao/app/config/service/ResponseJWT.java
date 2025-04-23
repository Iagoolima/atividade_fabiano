package com.autenticacao.app.config.service;

import lombok.Getter;

@Getter
public class ResponseJWT {
    private String token;

    public ResponseJWT(String token) {
        this.token = token;
    }
}
