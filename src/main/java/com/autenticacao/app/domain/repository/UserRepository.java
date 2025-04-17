package com.autenticacao.app.domain.repository;

import com.autenticacao.app.adapter.entity.UserEntity;
import com.autenticacao.app.domain.model.User;

public interface UserRepository {
    Boolean existsByEmail(String email);
    void saveUser(User user);
    UserEntity findByEmail(String email);
}
