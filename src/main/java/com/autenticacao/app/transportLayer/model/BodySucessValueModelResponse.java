package com.autenticacao.app.transportLayer.model;

import com.autenticacao.app.config.service.ResponseJWT;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BodySucessValueModelResponse {
    private LocalDateTime dateTime = LocalDateTime.now();
    private int code;
    private Object value;
    private ResponseJWT token;
}
