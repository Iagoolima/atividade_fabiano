package com.autenticacao.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ValidateEmail {
    private Long id;
    private String email;
    private String code;
    private Boolean validated;
    private LocalDateTime sentTime;
    private LocalDateTime expirationTime;

    public ValidateEmail() {
    }

    public ValidateEmail(String email, String code, Boolean validated, LocalDateTime sentTime, LocalDateTime expirationTime) {
        this.email = email;
        this.code = code;
        this.validated = validated;
        this.sentTime = sentTime;
        this.expirationTime = expirationTime;
    }

}
