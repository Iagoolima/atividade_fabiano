package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.business.Business;
import com.autenticacao.app.domain.model.SucessValueResponse;
import com.autenticacao.app.transportLayer.model.BodySucessValueModelResponse;
import com.autenticacao.app.transportLayer.model.BodyUserModelResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerifyController {

    private final ModelMapper mapper;

    @PostMapping("/find/user")
    public ResponseEntity<BodySucessValueModelResponse> verfifyRoleUser() {
        var user = Business.getInstance().getUser();
        var response = new SucessValueResponse(mapper.map(user, BodyUserModelResponse.class));

        var responseBody = mapper.map(response, BodySucessValueModelResponse.class);

        if(Business.getInstance().getTypeToken().equals("refresh")) {
            var token = Business.getInstance().getToken();
            responseBody.setToken(token);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @PostMapping("/find/admin")
    public ResponseEntity<BodySucessValueModelResponse> verifyRoleAdmin() {
        var user = Business.getInstance().getUser();
        var response = new SucessValueResponse(mapper.map(user, BodyUserModelResponse.class));

        var responseBody = mapper.map(response, BodySucessValueModelResponse.class);

        if(Business.getInstance().getTypeToken().equals("refresh")) {
            var token = Business.getInstance().getToken();
            responseBody.setToken(token);
        }
        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
    }
}
