package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BodyLoginUserModelRequest {
    @NotBlank(message = "Campo e-mail necessario.")
    @Email(message = "Campo e-mail incorreto")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo senha necessario.")
    @Length(min = 4, max = 50, message = "Sua senha deve conter no minimo 4 caracteres")
    @JsonProperty("password")
    private String password;
}
