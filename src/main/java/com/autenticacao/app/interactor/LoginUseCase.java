package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.UserRepositoryImpl;
import com.autenticacao.app.config.service.JwtServiceImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.model.LoginUser;
import com.autenticacao.app.domain.model.SucessValueResponse;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.utils.Encrypt;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepositoryImpl userRepository;

    private final MessageError messageError;

    private final Encrypt encrypt;

    private final JwtServiceImpl jwtService;

    public SucessValueResponse login(LoginUser loginUser) throws JOSEException {
        var user = findByEmail(loginUser);
        validatedPassword(loginUser, user);

        var token = jwtService.generateToken(user);

        return new SucessValueResponse(token);
    }

    private User findByEmail(LoginUser loginUser) {
        var user = userRepository.findByEmail(loginUser.getEmail().toLowerCase());
        if(user == null)
            throw new GeneralErrorException(messageError.EMAIL_NOT_FOUND);

        return user;
    }

    private void validatedPassword(LoginUser login, User user) {
        var passwordLogin = encrypt.getPasswordEncrypt(
                login.getPassword(),
                user.getUuidUser()
        );

        if(!encrypt.comparePasswordEncrypt(passwordLogin, user.getPassword()))
            throw new GeneralErrorException(messageError.PASSWORD_INCORRECT);
    }
}
