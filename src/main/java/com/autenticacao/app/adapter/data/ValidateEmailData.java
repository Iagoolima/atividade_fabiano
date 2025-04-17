package com.autenticacao.app.adapter.data;

import com.autenticacao.app.adapter.entity.ValidateEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidateEmailData extends JpaRepository<ValidateEmailEntity, Long> {
    Optional<ValidateEmailEntity> findByEmail(String email);
}
