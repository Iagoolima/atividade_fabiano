package com.autenticacao.app.domain.repository;

import com.autenticacao.app.domain.model.User;

public interface UserRepository {
    Boolean existsByEmail(String email);
    void saveUser(User user);
    User saveUserAndReturn(User user);
    User findByEmail(String email);
    User findById(User user);
}
