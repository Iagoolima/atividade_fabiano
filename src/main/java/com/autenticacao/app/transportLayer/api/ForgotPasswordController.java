package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.model.EmailUser;
import com.autenticacao.app.domain.model.UpdatePassword;
import com.autenticacao.app.interactor.SendEmailForgotPasswordUseCase;
import com.autenticacao.app.interactor.UpdatePasswordForgotPasswordUseCase;
import com.autenticacao.app.transportLayer.model.BodyEmailUserModelRequest;
import com.autenticacao.app.transportLayer.model.BodySucessMessageModelResponse;
import com.autenticacao.app.transportLayer.model.BodyUpdatePasswordModelRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ModelMapper mapper;

    private final SendEmailForgotPasswordUseCase sendEmailForgotPasswordUseCase;

    private final UpdatePasswordForgotPasswordUseCase updatePasswordForgotPasswordUseCase;

    @PostMapping("/send-email")
    public ResponseEntity<BodySucessMessageModelResponse> forgotPassword(@RequestBody BodyEmailUserModelRequest bodyEmailUserModel) {
        var emailUser = mapper.map(bodyEmailUserModel, EmailUser.class);
        var response = sendEmailForgotPasswordUseCase.sendEmail(emailUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.map(response, BodySucessMessageModelResponse.class));

    }

    @PostMapping("/update-password/{tokenUpdate}")
    public ResponseEntity<BodySucessMessageModelResponse> updatePassword(@PathVariable UUID tokenUpdate, @Valid @RequestBody BodyUpdatePasswordModelRequest bodyUpdatePasswordModelRequest) {
        var updatePassword = new UpdatePassword(
                tokenUpdate,
                bodyUpdatePasswordModelRequest.getPassword(),
                LocalDateTime.now()
        );
        var response = updatePasswordForgotPasswordUseCase.updatePassword(updatePassword);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.map(response, BodySucessMessageModelResponse.class));

    }
}
