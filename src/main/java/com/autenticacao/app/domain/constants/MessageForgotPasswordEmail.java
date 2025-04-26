package com.autenticacao.app.domain.constants;

import org.springframework.stereotype.Component;

@Component
public class MessageForgotPasswordEmail {

    public final String TITLE = "Troque a senha";
    public final String MESSAGE = "Para restaurar sua senha, acesse: {0} \n Tempo de expiração de {1} minutos";
}
