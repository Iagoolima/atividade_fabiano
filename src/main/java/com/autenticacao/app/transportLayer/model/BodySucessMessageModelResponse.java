package com.autenticacao.app.transportLayer.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BodySucessMessageModelResponse {
    private LocalDateTime dateTime = LocalDateTime.now();
    private int code;
    private String message;
}
