package com.autenticacao.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePassword {
    private UUID tokenUpdate;
    private String password;
    private LocalDateTime dateTime;
}
