package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BodyLoginUserModelRequest {
    @NotBlank(message = "Campo e-mail necessario.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo senha necessario.")
    @JsonProperty("password")
    private String password;
}
