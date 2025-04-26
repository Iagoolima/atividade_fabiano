package com.autenticacao.app.domain.business;

import com.autenticacao.app.config.service.ResponseJWT;
import com.autenticacao.app.domain.model.User;

public class Business {

    private static Business instance;
    private User user;
    private ResponseJWT token;
    private String typeToken;

    private Business(User user, ResponseJWT token, String typeToken) {
        this.user = user;
        this.token = token;
        this.typeToken = typeToken;
    }

    public static void init(User user, ResponseJWT token, String typeToken) {
        instance = new Business(user, token, typeToken);
    }

    public static Business getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public ResponseJWT getToken() {
        return token;
    }

    public String getTypeToken() {
        return typeToken;
    }
}