package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.UserRepositoryImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.constants.MessageValidateEmail;
import com.autenticacao.app.domain.model.BodySendEmail;
import com.autenticacao.app.domain.model.EmailUser;
import com.autenticacao.app.domain.model.ValidateEmail;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.repository.ValidateEmailRepository;
import com.autenticacao.app.domain.utils.GenerateCode;
import com.autenticacao.app.transportLayer.model.BodySendEmailModel;
import com.autenticacao.app.transportLayer.sender.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
@Slf4j
public class SendEmailRegisterUserUseCase {

    @Autowired
    private MessageError messageError;

    @Autowired
    private MessageSucess messageSucess;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private MessageValidateEmail messageValidateEmail;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private ValidateEmailRepository validateEmailRepository;

    @Value("${minutes.expiration.validated.email}")
    private Long minutesExpiration;

    public SucessMessageResponse sendEmailRegisterUser(EmailUser emailUser) {
        userExists(emailUser);
        String codeExpiration = generateCode.generatedCodeValidateEmail();
        var bodyEmail = createBodyEmail(emailUser, codeExpiration);
        var bodySendEmailModel = mapper.map(bodyEmail, BodySendEmailModel.class);
        emailSender.send(bodySendEmailModel);
        saveEmailValidationRecord(emailUser, codeExpiration);
        log.info("{}: {}", messageSucess.EMAIL_SENT_SUCESSFULLY, emailUser.getEmail());
        return new SucessMessageResponse(messageSucess.EMAIL_SENT_SUCESSFULLY);
    }

    private void userExists(EmailUser emailUser) {
        if(userRepository.existsByEmail(emailUser.getEmail().toLowerCase()))
            throw new RuntimeException(messageError.EMAIL_IS_EXIST);

        var validateEmail = validateEmailRepository.findByEmail(emailUser.getEmail().toLowerCase());
        if(validateEmail != null) {
            validateEmailRepository.deleteById(validateEmail.getId());
        }
    }

    private BodySendEmail createBodyEmail(EmailUser emailUser, String codeExpiration) {
        return new BodySendEmail(
                messageValidateEmail.TITLE,
                MessageFormat.format(messageValidateEmail.MESSAGE, codeExpiration, minutesExpiration),
                emailUser.getEmail()
        );
    }

    private void saveEmailValidationRecord(EmailUser emailUser, String codeExpiration) {
        var validateEmail = new ValidateEmail(
                emailUser.getEmail().toLowerCase(),
                codeExpiration,
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(minutesExpiration)
        );

        validateEmailRepository.saveValidateEmail(validateEmail);
    }
}
