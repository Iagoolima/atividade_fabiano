package com.autenticacao.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User {
    private Long id;
    private UUID uuidUser;
    private String email;
    private String name;
    private String password;
    private String role;
}
