package com.autenticacao.app.config.service;

import com.autenticacao.app.config.security.CustomUserDetails;
import com.autenticacao.app.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository
                .findByEmail(email);
        if (user == null) {
            return null;
        }
        String password = user.getPassword() != null ? String.valueOf(user.getPassword()) : "";

        return new CustomUserDetails(
                user,
                user.getName(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))        );
    }
}