package com.autenticacao.app.interactor;

import com.autenticacao.app.adapter.repositoryImpl.ValidateEmailRepositoryImpl;
import com.autenticacao.app.domain.constants.MessageError;
import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.config.exception.GeneralErrorException;
import com.autenticacao.app.domain.model.ConfirmEmail;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.model.ValidateEmail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmEmailUseCase {

    @Autowired
    private ValidateEmailRepositoryImpl validateEmailRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MessageError messageError;

    @Autowired
    private MessageSucess messageSucess;

    public SucessMessageResponse confirmEmail(ConfirmEmail confirmEmail) {
        var validateEmail = findRegister(confirmEmail);
        validateTime(confirmEmail, validateEmail);
        validateCode(confirmEmail, validateEmail);
        emailValidated(validateEmail);

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
