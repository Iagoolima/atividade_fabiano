package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.ValidateEmailRepositoryImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.model.ConfirmEmail;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.model.ValidateEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfirmEmailUseCase {

    private final ValidateEmailRepositoryImpl validateEmailRepository;

    private final ModelMapper mapper;

    private final MessageError messageError;

    private final MessageSucess messageSucess;

    public SucessMessageResponse confirmEmail(ConfirmEmail confirmEmail) {
        var validateEmail = findRegister(confirmEmail);
        validateTime(confirmEmail, validateEmail);
        validateCode(confirmEmail, validateEmail);
        emailValidated(validateEmail);
        log.info("{}: {}", messageSucess.VALIDATED_EMAIL_SENT_SUCESSFULLY, confirmEmail.getEmail());
        return new SucessMessageResponse(messageSucess.VALIDATED_EMAIL_SENT_SUCESSFULLY);
    }

    private ValidateEmail findRegister(ConfirmEmail confirmEmail) {
        var result = validateEmailRepository.findByEmail(confirmEmail.getEmail().toLowerCase());
        if(result == null)
            throw new GeneralErrorException(messageError.EMAIL_NOT_FOUND);


        return mapper.map(result, ValidateEmail.class);
    }

    private void validateTime(ConfirmEmail confirmEmail, ValidateEmail validateEmail) {
        if(confirmEmail.getDateTime().isAfter(validateEmail.getExpirationTime())) {
            validateEmailRepository.deleteById(validateEmail.getId());
            throw new GeneralErrorException(messageError.TIMER_EXPIRED);
        }
    }

    private void validateCode(ConfirmEmail confirmEmail, ValidateEmail validateEmail) {
        if(!confirmEmail.getCode().equalsIgnoreCase(validateEmail.getCode()))
            throw new GeneralErrorException(messageError.CODE_INCORRECT);
    }

    private void emailValidated(ValidateEmail validateEmail) {
        validateEmail.setValidated(true);
        validateEmailRepository.saveValidateEmail(validateEmail);
    }

}
