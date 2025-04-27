package com.autenticacao.app.interactor;

import com.autenticacao.app.domain.constants.MessageSucess;
import com.autenticacao.app.domain.model.SucessMessageResponse;
import com.autenticacao.app.domain.model.User;
import com.autenticacao.app.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteUserUseCase {

    private final UserRepository repository;

    private final MessageSucess messageSucess;

    public SucessMessageResponse deleteUser(User user) {
        repository.deleteById(user.getId());
        log.info("{}: {}", messageSucess.USER_DELETED_SUCESS, user.getEmail());
        return new SucessMessageResponse(messageSucess.USER_DELETED_SUCESS);
    }
}
