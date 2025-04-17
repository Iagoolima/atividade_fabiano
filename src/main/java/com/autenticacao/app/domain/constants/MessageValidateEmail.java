package com.autenticacao.app.domain.constants;

import org.springframework.stereotype.Component;

@Component
public class MessageValidateEmail {
    public final String TITLE = "CODIGO DE VALIDAÇÃO DE EMAIL";
    public final String MESSAGE = "Codigo de validação: {0}  \n Tempo de expiração de 10 minutos ";
}
