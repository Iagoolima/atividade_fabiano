package com.autenticacao.app.adapter.data;

import com.autenticacao.app.adapter.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserData extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);
}
