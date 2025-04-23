package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.UserRepositoryImpl;
import com.autenticacao.app.adapter.repositoryImpl.ValidateEmailRepositoryImpl;
import com.autenticacao.app.config.service.JwtServiceImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.model.SucessValueResponse;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.model.ValidateEmail;
import com.autenticacao.app.domain.utils.Encrypt;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterFinalUserUseCase {

    private final Encrypt encrypt;

    private final UserRepositoryImpl userRepository;

    private final ValidateEmailRepositoryImpl validateEmailRepository;

    private final MessageError messageError;

    private final MessageSucess messageSucess;

    private final ModelMapper mapper;

    private final JwtServiceImpl jwtService;

    public SucessValueResponse register(User user) throws JOSEException {
        userExists(user);
        ProcessValidatedEmail(user);
        user = generatedPassword(user);
        var registeredUser = saveUser(user);
        var token = jwtService.generateToken(registeredUser);

        return new SucessValueResponse(token);
    }

    private void userExists(User user) {
        if(userExists(user.getEmail())){
            throw new GeneralErrorException(messageError.EMAIL_IS_EXIST);
        }
    }

    private boolean userExists(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }

    private User saveUser(User user) {
        return userRepository.saveUserAndReturn(user);
    }

    private void ProcessValidatedEmail(User user) {
        var validateEmail = findvalidateEmail(user);
        compareEmailValidation(validateEmail);
    }

    private ValidateEmail findvalidateEmail(User user) {
        var validateEmailModel = validateEmailRepository.findByEmail(user.getEmail().toLowerCase());
        if (validateEmailModel == null)
            throw new GeneralErrorException(messageError.EMAIL_NOT_FOUND);
        return mapper.map(validateEmailModel, ValidateEmail.class);
    }

    private void compareEmailValidation(ValidateEmail validateEmail) {
        if(!validateEmail.getValidated())
            throw new GeneralErrorException(messageError.EMAIL_NOT_VALIDATED);
    }

    private User generatedPassword(User user) {
        user.setUuidUser(UUID.randomUUID());
        user.setPassword(
                encrypt.encryptPassword(
                        user.getPassword(),
                        user.getUuidUser()
                )
        );
        return user;
    }
}
