package com.autenticacao.app.adapter.repositoryImpl;

import com.autenticacao.app.adapter.data.UserData;
import com.autenticacao.app.adapter.model.UserModel;
import com.autenticacao.app.domain.entity.User;
import com.autenticacao.app.domain.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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
        UserModel userModel = mapper.map(user, UserModel.class);
        data.save(userModel);
    }

}
