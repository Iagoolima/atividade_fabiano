package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.business.Business;
import com.autenticacao.app.interactor.DeleteUserUseCase;
import com.autenticacao.app.transportLayer.model.BodySucessMessageModelResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper mapper;

    private final DeleteUserUseCase deleteUserUseCase;

    @DeleteMapping("/delete")
    public ResponseEntity<BodySucessMessageModelResponse> verifyRoleAdmin() {
        var user = Business.getInstance().getUser();
        var response = deleteUserUseCase.deleteUser(user);

        var responseBody = mapper.map(response, BodySucessMessageModelResponse.class);

        if(Business.getInstance().getTypeToken().equals("refresh")) {
            var token = Business.getInstance().getToken();
            responseBody.setToken(token);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
