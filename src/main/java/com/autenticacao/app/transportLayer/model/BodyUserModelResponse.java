package com.autenticacao.app.transportLayer.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BodyUserModelResponse {
    private String email;
    private String name;
    private String password;
    private String role;
}
