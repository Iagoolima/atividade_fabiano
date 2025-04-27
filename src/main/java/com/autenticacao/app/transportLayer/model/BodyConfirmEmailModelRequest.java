package com.autenticacao.app.transportLayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BodyConfirmEmailModelRequest {
    @NotBlank(message = "Campo e-mail necessario.")
    @Email(message = "Campo e-mail incorreto")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo de codigo de verificação necessario.")
    @Length(min = 4, max = 4, message = "O codigo de verificação deve ter exatamente 4 dígitos.")
    @JsonProperty("code")
    private String code;
}
