package com.autenticacao.app.domain.model;


import lombok.AllArgsConstructor;
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
}
