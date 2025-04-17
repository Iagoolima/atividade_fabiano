package com.autenticacao.app.config.service;

import com.autenticacao.app.adapter.entity.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class JwtServiceImpl {

    @Value("${jwt.acesso-expiracao}")
    private String acessExpiration;

    @Value("${jwt.chave-assinatura}")
    private String signatureKey;

    @Value("${jwt.chave-criptografia}")
    private String keyEcripty;

    private SecretKey getSecretKey() {
        byte[] chaveBytes = Base64.getDecoder().decode(keyEcripty);
        return new SecretKeySpec(chaveBytes, "AES");
    }

    public String generateToken(UserEntity user) throws JOSEException {
        long exp = Long.parseLong(acessExpiration);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(exp);
        Instant instant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date data = Date.from(instant);

        String expirationDateTimeToken = expirationDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        JWTClaimsSet claims = new JWTClaimsSet.Builder().subject(user.getEmail())
                .expirationTime(data)
                .claim("timeExpirition", expirationDateTimeToken)
                .claim("id", user.getId().toString())
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);

        signedJWT.sign(new MACSigner(signatureKey));

        JWEObject jweObject = new JWEObject(new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM),
                new Payload(signedJWT));

        jweObject.encrypt(new DirectEncrypter(getSecretKey()));

        return jweObject.serialize();
    }

    public JWTClaimsSet getClaims(String token) throws Exception {
        JWEObject jweObject = JWEObject.parse(token);
        jweObject.decrypt(new DirectDecrypter(getSecretKey()));

        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
        if (signedJWT == null) {
            throw new IllegalArgumentException("Invalid JWE payload.");
        }

        return signedJWT.getJWTClaimsSet();
    }

    public boolean isTokenValido(String token) {
        try {
            JWTClaimsSet claims = getClaims(token);
            java.util.Date dataEx = claims.getExpirationTime();
            LocalDateTime dataExpiration = dataEx.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            Boolean isRefreshToken = (Boolean) claims.getClaim("refreshToken");
            if (isRefreshToken != null && isRefreshToken) {
                return !LocalDateTime.now().isAfter(dataExpiration);
            }

            return !LocalDateTime.now().isAfter(dataExpiration);
        }
        catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) {
        try {
            JWTClaimsSet claims = getClaims(token);
            return claims.getSubject();
        }
        catch (Exception e) {
            return null;
        }
    }



}