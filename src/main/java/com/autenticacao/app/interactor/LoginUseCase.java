package com.autenticacao.app.interactor;

import com.autenticacao.app.config.service.JwtServiceImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.model.LoginUser;
import com.autenticacao.app.domain.model.SucessValueResponse;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.repository.UserRepository;
import com.autenticacao.app.domain.utils.Encrypt;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUseCase {

    private final UserRepository userRepository;

    private final MessageError messageError;

    private final Encrypt encrypt;

    private final JwtServiceImpl jwtService;

    private final MessageSucess messageSucess;

    public SucessValueResponse login(LoginUser loginUser) throws JOSEException {
        var user = findByEmail(loginUser);
        validatedPassword(loginUser, user);
        var token = jwtService.generateToken(user);
        log.info("{}: {}", messageSucess.USER_CONECTED, user.getEmail());
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

        if(!encrypt.comparePasswordEncrypt(passwordLogin, user.getPassword())) {
            throw new GeneralErrorException(messageError.PASSWORD_INCORRECT);
        }
    }
}
