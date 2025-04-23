package com.autenticacao.app.adapter.repositoryImpl;

import com.autenticacao.app.adapter.data.ValidateEmailData;
import com.autenticacao.app.adapter.entity.ValidateEmailEntity;
import com.autenticacao.app.domain.model.ValidateEmail;
import com.autenticacao.app.domain.repository.ValidateEmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Primary
@Component
public class ValidateEmailRepositoryImpl implements ValidateEmailRepository {

    @Autowired
    private ValidateEmailData data;

    @Autowired
    private ModelMapper mapper;

    public void saveValidateEmail(ValidateEmail validateEmail) {
        var validateEmailModel = mapper.map(validateEmail, ValidateEmailEntity.class);
        data.save(validateEmailModel);
    }

    public ValidateEmail findByEmail(String email) {
        Optional<ValidateEmailEntity> validateUserEmail = data.findByEmail(email);
        return validateUserEmail.map(validateEmailEntity -> mapper.map(validateEmailEntity, ValidateEmail.class)).orElse(null);
    }

    public void deleteById(Long id) {
        data.deleteById(id);
    }
}
