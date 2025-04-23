package com.autenticacao.app.domain.business;

import com.autenticacao.app.domain.model.User;

public class Business {

    private static Business instance;
    private User user;

    private Business(User user) {
        this.user = user;
    }

    public static void init(User user) {
        if (instance == null) {
            instance = new Business(user);
        }
    }

    public static Business getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Business not initialized.");
        }
        return instance;
    }

    public User getUser() {
        return user;
    }
}
