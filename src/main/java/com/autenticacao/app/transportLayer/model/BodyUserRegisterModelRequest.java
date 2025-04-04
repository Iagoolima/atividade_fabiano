package com.autenticacao.app.transportLayer.model;

public record BodyUserRegisterModelRequest( String email, String name, String password, String role) {
}
