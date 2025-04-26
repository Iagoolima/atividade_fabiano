package com.autenticacao.app.adapter.data;

import com.autenticacao.app.adapter.entity.ForgotPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ForgotPasswordData extends JpaRepository<ForgotPasswordEntity,Long> {
    Optional<ForgotPasswordEntity> findByEmail(String email);
    Optional<ForgotPasswordEntity> findByTokenUpdate(UUID tokenUpdate);
}
