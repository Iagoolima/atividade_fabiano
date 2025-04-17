package com.autenticacao.app.config.service;

import com.autenticacao.app.adapter.entity.UserEntity;
import com.autenticacao.app.adapter.repositoryImpl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByEmail(email);
        if (user == null) {
            return null;
        }
        String password = user.getPassword() != null ? String.valueOf(user.getPassword()) : "";

        return User.builder()
                .username(user.getEmail())
                .password(password)
                .roles("USER")
                .build();
    }
}