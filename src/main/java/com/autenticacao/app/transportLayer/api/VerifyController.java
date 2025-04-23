package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.config.exception.ErrorResponse;
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

    @PostMapping("/find/role_user")
    public ResponseEntity<?> verfifyRoleUser() {
        var user = Business.getInstance().getUser();
        try {
            var response = new SucessValueResponse(mapper.map(user, BodyUserModelResponse.class));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapper.map(response, BodySucessValueModelResponse.class));
        } catch(RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/find/role_admin")
    public ResponseEntity<?> verifyRoleAdmin() {
        var user = Business.getInstance().getUser();
        try {
            var response = new SucessValueResponse(mapper.map(user, BodyUserModelResponse.class));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapper.map(response, BodySucessValueModelResponse.class));
        } catch(RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}
