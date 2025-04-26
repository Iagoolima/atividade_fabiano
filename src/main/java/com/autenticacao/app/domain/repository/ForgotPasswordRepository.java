package com.autenticacao.app.domain.repository;

import com.autenticacao.app.domain.model.ForgotPassword;

import java.util.Optional;
import java.util.UUID;

public interface ForgotPasswordRepository {
    void save(ForgotPassword forgotPassword);
    ForgotPassword findByEmail(String email);
    void deleteById(Long id);
    ForgotPassword findByTokenUpdate(UUID tokenUpdate);
}
