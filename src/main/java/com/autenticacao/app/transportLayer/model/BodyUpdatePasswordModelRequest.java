package com.autenticacao.app.transportLayer.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class BodyUpdatePasswordModelRequest {
    @NotBlank(message = "Senha necessario")
    private String password;
}
