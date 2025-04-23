package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BodyConfirmEmailModelRequest {
    @NotBlank(message = "Campo e-mail necessario.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo de codigo de verificação necessario.")
    @JsonProperty("code")
    private String code;
}
