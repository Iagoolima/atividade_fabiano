package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BodyUserRegisterModelRequest {
    @NotBlank(message = "Campo e-mail necessario.")
    @Email(message = "Campo e-mail incorreto")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo de nome necessario.")
    @Length(min = 4, max = 100, message = "Seu nome deve conter no minimo 4 caracteres")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Campo de senha necessario.")
    @Length(min = 4, max = 50, message = "Sua senha deve conter no minimo 4 caracteres")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Campo de permissão do usuário necessário.")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "O campo deve ser 'ADMIN' ou 'USER'.")
    @JsonProperty("role")
    private String role;
}
