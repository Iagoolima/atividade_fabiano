package com.autenticacao.app.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorFieldResponse {
    private int code = HttpStatus.BAD_REQUEST.value();
    private List<String> errorField;

    public ErrorFieldResponse(List<String> errorField) {
        this.errorField = errorField;
    }
}
