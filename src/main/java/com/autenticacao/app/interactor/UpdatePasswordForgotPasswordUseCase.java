package com.autenticacao.app.interactor;

import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.model.ForgotPassword;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.model.UpdatePassword;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.repository.ForgotPasswordRepository;
import com.autenticacao.app.domain.repository.UserRepository;
import com.autenticacao.app.domain.utils.Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatePasswordForgotPasswordUseCase {

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final MessageError messageError;

    private final MessageSucess messageSucess;

    private final Encrypt encrypt;

    private final UserRepository userRepository;

    public SucessMessageResponse updatePassword(UpdatePassword updatePassword) {
        var forgotPassword = findByTokenUpdate(updatePassword);
        validateDate(updatePassword, forgotPassword);
        var user = updatePasswordUser(updatePassword, forgotPassword.getIdUser());
        saveUser(user);
        deleteForgotPassword(forgotPassword);
        log.info("{}: {}", messageSucess.UPDATE_PASSWORD, user.getEmail());
        return new SucessMessageResponse(messageSucess.UPDATE_PASSWORD);
    }

    private ForgotPassword findByTokenUpdate(UpdatePassword updatePassword){
        var response = forgotPasswordRepository.findByTokenUpdate(updatePassword.getTokenUpdate());
        if(Objects.isNull(response))
            throw new GeneralErrorException(messageError.FORGOT_PASSWORD_NOT_FOUND);

        return response;
    }

    private void validateDate(UpdatePassword updatePassword, ForgotPassword forgotPassword) {
        if(updatePassword.getDateTime().isAfter(forgotPassword.getExpirationTime())) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            throw new GeneralErrorException(messageError.TIMER_EXPIRED);
        }
    }

    private User updatePasswordUser(UpdatePassword updatePassword, User user) {
        user.setPassword(
                encrypt.encryptPassword(updatePassword.getPassword(), user.getUuidUser())
        );
        return user;
    }
    private void saveUser(User user) {
        userRepository.saveUser(user);
    }

    private void deleteForgotPassword(ForgotPassword forgotPassword) {
        forgotPasswordRepository.deleteById(forgotPassword.getId());
    }
}
