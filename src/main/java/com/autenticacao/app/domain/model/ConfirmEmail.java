package com.autenticacao.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConfirmEmail {
    private String email;
    private String code;
    private LocalDateTime dateTime = LocalDateTime.now();
}
