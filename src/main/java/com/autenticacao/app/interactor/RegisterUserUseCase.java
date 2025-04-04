package com.autenticacao.app.interactor;

import com.autenticacao.app.domain.entity.User;
import com.autenticacao.app.domain.repository.UserRepository;
import com.autenticacao.app.domain.utils.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterUserUseCase {

    @Autowired
    private Encrypt encrypt;

    @Autowired
    private UserRepository repository;

    public void register(User user) {
        if(userExists(user.getEmail())){
            throw new RuntimeException("usuario já existe");
        }
        user = generatedPassword(user);
        saveUser(user);
    }

    private User generatedPassword(User user) {
        user.setIdUser(UUID.randomUUID());
        user.setPassword(
                encrypt.encryptPassword(
                        user.getPassword(),
                        user.getIdUser()
                )
        );
        return user;
    }

    public boolean userExists(String email) {
        return repository.existsByEmail(email.toLowerCase());
    }

    public void saveUser(User user) {
        repository.saveUser(user);
    }



}
