package com.autenticacao.app.transportLayer.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BodyUpdatePasswordModelRequest {
    @NotBlank(message = "Campo de senha necessaria")
    @Length(min = 4, max = 50, message = "Sua senha deve conter no minimo 4 caracteres")
    private String password;
}
