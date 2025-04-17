package com.autenticacao.app.domain.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateCode {

    private static final String characters = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
    private static final int lengthValidateEmail = 4;

    public String generatedCodeValidateEmail() {
        return generatedCode(lengthValidateEmail);
    }

    private String generatedCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }



}
