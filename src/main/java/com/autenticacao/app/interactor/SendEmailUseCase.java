package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.UserRepositoryImpl;
import com.autenticacao.app.adapter.repositoryImpl.ValidateEmailRepositoryImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.constants.MessageValidateEmail;
import com.autenticacao.app.domain.model.BodySendEmail;
import com.autenticacao.app.domain.model.EmailUser;
import com.autenticacao.app.domain.model.ValidateEmail;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.utils.GenerateCode;
import com.autenticacao.app.transportLayer.model.BodySendEmailModel;
import com.autenticacao.app.transportLayer.sender.EmailSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Service
public class SendEmailUseCase {

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
    private ValidateEmailRepositoryImpl validateEmailRepository;

    @Value("${minutes.expiration.validated.email}")
    private Long minutesExpiration;

    public SucessMessageResponse sendEmailRegisterUser(EmailUser emailUser) {
        userExists(emailUser);
        String codeExpiration = generateCode.generatedCodeValidateEmail();
        var bodyEmail = createBodyEmail(emailUser, codeExpiration);
        var bodySendEmailModel = mapper.map(bodyEmail, BodySendEmailModel.class);
        emailSender.send(bodySendEmailModel);
        saveEmailValidationRecord(emailUser, codeExpiration);

        return new SucessMessageResponse(messageSucess.EMAIL_SENT_SUCESSFULLY);
    }

    private void userExists(EmailUser emailUser) {
        if(userRepository.existsByEmail(emailUser.getEmail()))
            throw new RuntimeException(messageError.EMAIL_IS_EXIST);

        var validateEmail = validateEmailRepository.findByEmail(emailUser.getEmail());
        if(validateEmail != null) {
            validateEmailRepository.deleteById(validateEmail.getId());
        }
    }

    private BodySendEmail createBodyEmail(EmailUser emailUser, String codeExpiration) {
        return new BodySendEmail(
                messageValidateEmail.TITLE,
                MessageFormat.format(messageValidateEmail.MESSAGE, codeExpiration),
                emailUser.getEmail()
        );
    }

    private void saveEmailValidationRecord(EmailUser emailUser, String codeExpiration) {
        var validateEmail = new ValidateEmail();
        validateEmail.setValidated(false);
        validateEmail.setCode(codeExpiration);
        validateEmail.setSentTime(LocalDateTime.now());
        validateEmail.setExpirationTime(
                LocalDateTime.now().plusMinutes(minutesExpiration)
        );

        validateEmailRepository.saveValidateEmail(validateEmail);
    }
}
