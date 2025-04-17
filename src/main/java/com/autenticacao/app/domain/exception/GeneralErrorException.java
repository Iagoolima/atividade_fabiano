package com.autenticacao.app.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralErrorException extends RuntimeException{
    public GeneralErrorException(String message) { super(message);}
}
