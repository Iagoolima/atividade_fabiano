package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.business.Business;
import com.autenticacao.app.domain.model.LoginUser;
import com.autenticacao.app.interactor.LoginUseCase;
import com.autenticacao.app.transportLayer.model.BodyLoginUserModelRequest;
import com.autenticacao.app.transportLayer.model.BodySucessValueModelResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @PostMapping("/login")
    public ResponseEntity<BodySucessValueModelResponse> sendEmail(@RequestBody @Valid BodyLoginUserModelRequest loginUserModelRequest) {
        var loginUser = mapper.map(loginUserModelRequest, LoginUser.class);

        var response = loginUseCase.login(loginUser);

        var responseBody = mapper.map(response, BodySucessValueModelResponse.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
