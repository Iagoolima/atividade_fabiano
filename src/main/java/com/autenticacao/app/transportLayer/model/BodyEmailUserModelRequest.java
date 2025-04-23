package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BodyEmailUserModelRequest {
    @NotBlank(message = "Campo e-mail necessario.")
    @JsonProperty("email")
    private String email;
}
