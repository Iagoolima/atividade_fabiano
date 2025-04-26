package com.autenticacao.app.domain.repository;

import com.autenticacao.app.adapter.entity.ValidateEmailEntity;
import com.autenticacao.app.domain.model.ValidateEmail;

public interface ValidateEmailRepository {
    void saveValidateEmail(ValidateEmail validateEmail);
    ValidateEmail findByEmail(String email);
    void deleteById(Long id);
}
