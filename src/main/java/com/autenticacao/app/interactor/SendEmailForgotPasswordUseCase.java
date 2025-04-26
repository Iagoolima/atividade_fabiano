package com.autenticacao.app.interactor;

import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageForgotPasswordEmail;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.model.*;
import com.autenticacao.app.domain.repository.ForgotPasswordRepository;
import com.autenticacao.app.domain.repository.UserRepository;
import com.autenticacao.app.transportLayer.model.BodySendEmailModel;
import com.autenticacao.app.transportLayer.sender.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailForgotPasswordUseCase {

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final UserRepository userRepository;

    private final MessageError messageError;

    private final MessageSucess messageSucess;

    private final MessageForgotPasswordEmail messageForgotPasswordEmail;

    private final ModelMapper mapper;

    @Value("${minutes.expiration.forgot.password}")
    private Long minutesExpiration;

    @Value("${url.forgot.password}")
    private String linkForgotPassword;

    private final EmailSender emailSender;

    public SucessMessageResponse sendEmail(EmailUser emailUser) {
        var user = userExists(emailUser);
        existsRegisterForgotPassword(user);
        var forgotPassword = buildForgotPassword(user);
        var bodySendEmail = createBodySendEmail(forgotPassword);
        sendEmail(bodySendEmail);
        saveForgotPassword(forgotPassword);

        return new SucessMessageResponse(messageSucess.EMAIL_SENT_SUCESSFULLY);
    }

    private User userExists(EmailUser email) {
        var user = userRepository.findByEmail(email.getEmail().toLowerCase());
        if(Objects.isNull(user))
            throw new GeneralErrorException(messageError.EMAIL_NOT_FOUND);

        return user;
    }

    private ForgotPassword buildForgotPassword(User user) {
            return new ForgotPassword(
                    user.getEmail().toLowerCase(),
                    UUID.randomUUID(),
                    user,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(minutesExpiration)
            );
    }

    private BodySendEmail createBodySendEmail(ForgotPassword forgotPassword) {
        var link = linkForgotPassword + forgotPassword.getTokenUpdate();
        return new BodySendEmail(
                messageForgotPasswordEmail.TITLE,
                MessageFormat.format(messageForgotPasswordEmail.MESSAGE, link, minutesExpiration),
                forgotPassword.getEmail()
        );
    }

    private void sendEmail(BodySendEmail bodySendEmail) {
        emailSender.send(mapper.map(bodySendEmail, BodySendEmailModel.class));
    }

    private void saveForgotPassword(ForgotPassword forgotPassword) {
        forgotPasswordRepository.save(forgotPassword);
    }

    private void existsRegisterForgotPassword(User user) {
        var forgotPassword = forgotPasswordRepository.findByEmail(user.getEmail().toLowerCase());
        if(Objects.nonNull(forgotPassword))
            forgotPasswordRepository.deleteById(forgotPassword.getId());
    }

}
