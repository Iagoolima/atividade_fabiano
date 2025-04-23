package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.config.exception.ErrorResponse;
import com.autenticacao.app.domain.model.LoginUser;
import com.autenticacao.app.interactor.LoginUseCase;
import com.autenticacao.app.transportLayer.model.BodyLoginUserModelRequest;
import com.autenticacao.app.transportLayer.model.BodySucessValueModelResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final ModelMapper mapper;

    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public ResponseEntity<?> sendEmail(@RequestBody @Valid BodyLoginUserModelRequest loginUserModelRequest) {
        var loginUser = mapper.map(loginUserModelRequest, LoginUser.class);
        try {
            var response = loginUseCase.login(loginUser);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapper.map(response, BodySucessValueModelResponse.class));
        } catch(RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
