package com.autenticacao.app.transportLayer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodySendEmailModel {
    private String title;
    private String message;
    private String email;
}
