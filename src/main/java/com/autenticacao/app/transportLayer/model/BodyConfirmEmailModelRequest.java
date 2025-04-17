package com.autenticacao.app.transportLayer.model;

import lombok.Getter;

@Getter
public class BodyConfirmEmailModelRequest {
    private String email;
    private String code;
}
