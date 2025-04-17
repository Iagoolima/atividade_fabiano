package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.UserRepositoryImpl;
import com.autenticacao.app.adapter.repositoryImpl.ValidateEmailRepositoryImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.model.ValidateEmail;
import com.autenticacao.app.domain.utils.Encrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterFinalUserUseCase {

    @Autowired
    private Encrypt encrypt;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private ValidateEmailRepositoryImpl validateEmailRepository;

    @Autowired
    private MessageError messageError;

    @Autowired
    private MessageSucess messageSucess;

    @Autowired
    private ModelMapper mapper;

    public SucessMessageResponse register(User user) {
        userExists(user);
        ProcessValidatedEmail(user);
        user = generatedPassword(user);
        saveUser(user);

        return new SucessMessageResponse(messageSucess.REGISTERED_USER);
    }

    private void userExists(User user) {
        if(userExists(user.getEmail())){
            throw new RuntimeException("usuario já existe");
        }
    }

    private boolean userExists(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }

    private void saveUser(User user) {
        userRepository.saveUser(user);
    }

    private void ProcessValidatedEmail(User user) {
        var validateEmail = findvalidateEmail(user);
        compareEmailValidation(validateEmail);
    }

    private ValidateEmail findvalidateEmail(User user) {
        var validateEmailModel = validateEmailRepository.findByEmail(user.getEmail());
        if (validateEmailModel == null)
            throw new RuntimeException(messageError.EMAIL_NOT_FOUND);
        return mapper.map(validateEmailModel, ValidateEmail.class);
    }

    private void compareEmailValidation(ValidateEmail validateEmail) {
        if(!validateEmail.getValidated())
            throw new RuntimeException(messageError.EMAIL_NOT_VALIDATED);
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
}
