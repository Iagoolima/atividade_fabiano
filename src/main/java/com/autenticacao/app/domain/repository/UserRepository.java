package com.autenticacao.app.domain.repository;

import com.autenticacao.app.domain.entity.User;

public interface UserRepository {
    Boolean existsByEmail(String email);
    void saveUser(User user);
}
