package com.autenticacao.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BodySendEmail {
    private String title;
    private String message;
    private String email;
}
