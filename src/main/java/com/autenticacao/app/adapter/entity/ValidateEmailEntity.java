package com.autenticacao.app.adapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "validacao_email")
public class ValidateEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String code;
    private Boolean validated;
    private LocalDateTime sentTime;
    private LocalDateTime expirationTime;
}
