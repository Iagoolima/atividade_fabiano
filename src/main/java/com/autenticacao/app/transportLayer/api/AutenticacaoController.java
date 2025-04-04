package com.autenticacao.app.transportLayer.api;

import com.autenticacao.app.domain.entity.User;
import com.autenticacao.app.interactor.RegisterUserUseCase;
import com.autenticacao.app.transportLayer.model.BodyUserRegisterModelRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    public ResponseEntity<?> cadastrarUsuario(@RequestBody BodyUserRegisterModelRequest userModelRequest){

        User user = mapper.map(userModelRequest, User.class);
        registerUserUseCase.register(user);

        return ResponseEntity.ok().build();
    }


}
