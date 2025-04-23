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
        var userEntity = mapper.map(user, UserEntity.class);
        data.save(userEntity);
    }

    @Override
    public User saveUserAndReturn(User user) {
        var userEntity = mapper.map(user, UserEntity.class);
        var savedUser = data.save(userEntity);
        return mapper.map(savedUser, User.class);

    }

    public User findByEmail(String email) {
        Optional<UserEntity> user =  data.findByEmail(email);
        return user.map(userEntity -> mapper.map(userEntity, User.class)).orElse(null);
    }

    public User findById(User user) {
        var userEntity = mapper.map(user, UserEntity.class);
        Optional<UserEntity> userData =  data.findById(userEntity.getId());
        return userData.map(userEnt -> mapper.map(userEntity, User.class)).orElse(null);
    }

}
