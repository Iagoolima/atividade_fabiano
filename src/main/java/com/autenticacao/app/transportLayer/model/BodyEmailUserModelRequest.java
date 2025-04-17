package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BodyEmailUserModelRequest {
    @NotNull(message = "Campo e-mail necessario.")
    @NotBlank(message = "Campo e-mail necessario.")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Campo senha necessario.")
    @NotBlank(message = "Campo senha necessario.")
    @JsonProperty("senha")
    private String senha;

}
