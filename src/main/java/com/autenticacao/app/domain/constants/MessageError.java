package com.autenticacao.app.domain.constants;

import org.springframework.stereotype.Component;

@Component
public class MessageError {
    public final String EMAIL_IS_EXIST = "Email já cadastrado";
    public final String ERROR_SEND_EMAIL = "Erro ao enviar o email";
    public final String EMAIL_NOT_FOUND = "Email não encontrado";
    public final String EMAIL_NOT_VALIDATED = "Email não foi validado";
    public final String TIMER_EXPIRED = "Tempo expirado. por favor, tente novamente!";
    public final String CODE_INCORRECT = "Codigo de validação incorreto";
    public final String PASSWORD_INCORRECT = "Senha invalida";
    public final String USER_NOT_FOUND = "Usuario não encontrado";
}
