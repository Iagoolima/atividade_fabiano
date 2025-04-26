package com.autenticacao.app.adapter.repositoryImpl;

import com.autenticacao.app.adapter.data.ForgotPasswordData;
import com.autenticacao.app.adapter.entity.ForgotPasswordEntity;
import com.autenticacao.app.domain.model.ForgotPassword;
import com.autenticacao.app.domain.repository.ForgotPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Primary
@Component
@RequiredArgsConstructor
public class ForgotPasswordRepositoryImpl implements ForgotPasswordRepository {

    private final ForgotPasswordData data;

    private final ModelMapper mapper;

    @Override
    public void save(ForgotPassword forgotPassword) {
        var forgotPasswordEntity = mapper.map(forgotPassword, ForgotPasswordEntity.class);
        data.save(forgotPasswordEntity);
    }

    @Override
    public ForgotPassword findByEmail(String email) {
       Optional<ForgotPasswordEntity> optionalForgotPasswordEntity = data.findByEmail(email);
       if(optionalForgotPasswordEntity.isPresent())
           return mapper.map(optionalForgotPasswordEntity.get(), ForgotPassword.class);

       return null;
    }

    @Override
    public void deleteById(Long id) {
        data.deleteById(id);
    }

    @Override
    public ForgotPassword findByTokenUpdate(UUID tokenUpdate) {
        Optional<ForgotPasswordEntity> optionalForgotPasswordEntity = data.findByTokenUpdate(tokenUpdate);
        if(optionalForgotPasswordEntity.isPresent())
            return mapper.map(optionalForgotPasswordEntity.get(), ForgotPassword.class);

        return null;
    }
}
