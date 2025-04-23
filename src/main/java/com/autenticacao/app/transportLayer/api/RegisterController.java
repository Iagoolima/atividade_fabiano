package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.config.exception.ErrorResponse;
import com.autenticacao.app.domain.model.ConfirmEmail;
import com.autenticacao.app.domain.model.EmailUser;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.interactor.ConfirmEmailUseCase;
import com.autenticacao.app.interactor.RegisterFinalUserUseCase;
import com.autenticacao.app.interactor.SendEmailUseCase;
import com.autenticacao.app.transportLayer.model.*;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RegisterFinalUserUseCase registerFinalUserUseCase;

    @Autowired
    private SendEmailUseCase sendEmailUseCase;

    @Autowired
    private ConfirmEmailUseCase confirmEmailUseCase;

    @PostMapping("/validate-email")
    public ResponseEntity<?> sendEmail(@RequestBody  @Valid BodyEmailUserModelRequest emailUserModelRequest) {
        var email = mapper.map(emailUserModelRequest, EmailUser.class);
        try {
            var response = sendEmailUseCase.sendEmailRegisterUser(email);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapper.map(response, BodySucessMessageModelResponse.class));
        } catch(RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody @Valid BodyConfirmEmailModelRequest bodyConfirmEmailModelRequest) {
        var confirmEmail = mapper.map(bodyConfirmEmailModelRequest, ConfirmEmail.class);
        try{
            var response = confirmEmailUseCase.confirmEmail(confirmEmail);
            return ResponseEntity
                    .status(200)
                    .body(mapper.map(response, BodySucessMessageModelResponse.class));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/full")
    public ResponseEntity<?> registerFinal(@RequestBody @Valid BodyUserRegisterModelRequest userModelRequest) {
        var user = mapper.map(userModelRequest, User.class);
        try {
            var response = registerFinalUserUseCase.register(user);
            return ResponseEntity
                    .status(200)
                    .body(mapper.map(response, BodySucessValueModelResponse.class));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
