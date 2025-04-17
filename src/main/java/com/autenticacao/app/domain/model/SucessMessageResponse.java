package com.autenticacao.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SucessMessageResponse {
    private int code = HttpStatus.OK.value();
    private String message;

    public SucessMessageResponse(String message) {
        this.message = message;
    }
}



