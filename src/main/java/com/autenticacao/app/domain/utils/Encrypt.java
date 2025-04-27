package com.autenticacao.app.domain.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Encrypt {

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${encryption.salt}")
    private String soil;

    public String encryptPassword(String seed, UUID water) {
        return passwordEncoder.encode(soil + seed + water.toString());
    }

    public String getPasswordEncrypt(String seed, UUID water) {
        return soil + seed + water.toString();
    }

    public Boolean comparePasswordEncrypt(String password, String passwordData) {
        return passwordEncoder.matches(password, passwordData);
    }


}
