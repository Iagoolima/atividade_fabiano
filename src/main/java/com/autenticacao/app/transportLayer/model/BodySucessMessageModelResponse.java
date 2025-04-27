package com.autenticacao.app.transportLayer.model;

import com.autenticacao.app.config.service.ResponseJWT;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BodySucessMessageModelResponse {
    private LocalDateTime dateTime = LocalDateTime.now();
    private int code;
    private String message;
    private ResponseJWT token;
}
