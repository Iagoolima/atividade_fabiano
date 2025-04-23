package com.autenticacao.app.config.security;

import com.autenticacao.app.domain.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public User getUser() {
        return user;
    }

    public CustomUserDetails(User user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }
}
