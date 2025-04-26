package com.autenticacao.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ForgotPassword {
    private Long id;
    private String email;
    private UUID tokenUpdate;
    private User idUser;
    private LocalDateTime sentTime;
    private LocalDateTime expirationTime;

    public ForgotPassword(String email, UUID tokenUpdate, User idUser, LocalDateTime sentTime, LocalDateTime expirationTime) {
        this.email = email;
        this.tokenUpdate = tokenUpdate;
        this.idUser = idUser;
        this.sentTime = sentTime;
        this.expirationTime = expirationTime;
    }
    public ForgotPassword() {
    }
}
