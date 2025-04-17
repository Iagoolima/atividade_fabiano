package com.autenticacao.app.adapter.repositoryImpl;

import com.autenticacao.app.adapter.data.UserData;
import com.autenticacao.app.adapter.entity.UserEntity;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Primary
@Component
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserData data;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean existsByEmail(String email) {
        return data.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        var userModel = mapper.map(user, UserEntity.class);
        data.save(userModel);
    }

    public UserEntity findByEmail(String email) {
        Optional<UserEntity> user =  data.findByEmail(email);
        return user.orElse(null);
    }

}
