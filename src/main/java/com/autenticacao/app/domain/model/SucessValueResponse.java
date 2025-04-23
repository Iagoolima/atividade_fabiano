package com.autenticacao.app.domain.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SucessValueResponse {
    private int code = HttpStatus.OK.value();
    private Object value;

    public SucessValueResponse(Object value) {
        this.value = value;
    }

}
