package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.exception.ErrorResponse;
import com.autenticacao.app.domain.model.ConfirmEmail;
import com.autenticacao.app.domain.model.EmailUser;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.interactor.ConfirmEmailUseCase;
import com.autenticacao.app.interactor.RegisterFinalUserUseCase;
import com.autenticacao.app.interactor.SendEmailUseCase;
import com.autenticacao.app.transportLayer.model.BodyConfirmEmailModelRequest;
import com.autenticacao.app.transportLayer.model.BodyEmailUserModelRequest;
import com.autenticacao.app.transportLayer.model.BodyUserRegisterModelRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    .body(response);
        } catch(RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<SucessMessageResponse> confirmEamil(@RequestBody @Valid BodyConfirmEmailModelRequest bodyConfirmEmailModelRequest) {
        var confirmEmail = mapper.map(bodyConfirmEmailModelRequest, ConfirmEmail.class);
        var response = confirmEmailUseCase.confirmEmail(confirmEmail);

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/full")
    public ResponseEntity<SucessMessageResponse> registerFinal(@RequestBody BodyUserRegisterModelRequest userModelRequest) {
        var user = mapper.map(userModelRequest, User.class);
        var response = registerFinalUserUseCase.register(user);

        return ResponseEntity.status(201).body(response);
    }
}
