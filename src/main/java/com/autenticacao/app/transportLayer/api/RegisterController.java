package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.business.Business;
import com.autenticacao.app.domain.model.ConfirmEmail;
import com.autenticacao.app.domain.model.EmailUser;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.interactor.ConfirmEmailUseCase;
import com.autenticacao.app.interactor.RegisterFinalUserUseCase;
import com.autenticacao.app.interactor.SendEmailRegisterUserUseCase;
import com.autenticacao.app.transportLayer.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final ModelMapper mapper;

    private final RegisterFinalUserUseCase registerFinalUserUseCase;

    private final SendEmailRegisterUserUseCase sendEmailRegisterUserUseCase;

    private final ConfirmEmailUseCase confirmEmailUseCase;

    @PostMapping("/validate-email")
    public ResponseEntity<BodySucessMessageModelResponse> sendEmail(@RequestBody  @Valid BodyEmailUserModelRequest emailUserModelRequest) {
        var email = mapper.map(emailUserModelRequest, EmailUser.class);
            var response = sendEmailRegisterUserUseCase.sendEmailRegisterUser(email);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapper.map(response, BodySucessMessageModelResponse.class));
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<BodySucessMessageModelResponse> confirmEmail(@RequestBody @Valid BodyConfirmEmailModelRequest bodyConfirmEmailModelRequest) {
        var confirmEmail = mapper.map(bodyConfirmEmailModelRequest, ConfirmEmail.class);
            var response = confirmEmailUseCase.confirmEmail(confirmEmail);
            return ResponseEntity
                    .status(200)
                    .body(mapper.map(response, BodySucessMessageModelResponse.class));
    }

    @SneakyThrows
    @PostMapping("/full")
    public ResponseEntity<BodySucessValueModelResponse> registerFinal(@RequestBody @Valid BodyUserRegisterModelRequest userModelRequest) {
        var user = mapper.map(userModelRequest, User.class);
        var response = registerFinalUserUseCase.register(user);

        var responseBody = mapper.map(response, BodySucessValueModelResponse.class);

        var token = Business.getInstance().getToken();
        responseBody.setToken(token);

        return ResponseEntity
                    .status(200)
                    .body(responseBody);
    }
}
