package com.align.domain.entity;

import com.align.domain.config.TokenType;
import com.align.infrastructure.exception.BusinessException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Data
public class UserEntity {

    private static String name;

    private UserEntity(){}
    public static Boolean verifyPasswordRule(String password, String confirmedPassword) {

        if(!Objects.equals(password, confirmedPassword)) {
            throw new BusinessException(500, "Password and confirmed password do not match");
        }
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\da-zA-Z]).{8,}$";
        boolean passwordResult;
        boolean confirmedPasswordResult;
        passwordResult = password.matches(pattern);
        confirmedPasswordResult = confirmedPassword.matches(pattern);
        return passwordResult && confirmedPasswordResult;
    }
    public static String generateToken(String userId, TokenType tokenType, Map<String, Object> params) {
        String accessTokenSecret = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDR7";
        String refreshTokenSecret = "9dr6vsnXbZ/JjR3P07fu5m+Ysj0cArxlVVcJxNeDTMPswoICjN4Ho6RhXh";
        long currentTimeInMillis = System.currentTimeMillis();
        long expires = tokenType==TokenType.ACCESS_TOKEN ?
                30 * 60 * 1000 : 40 * 60 * 1000;
        String secret = tokenType==TokenType.ACCESS_TOKEN ? accessTokenSecret : refreshTokenSecret;
        Date expireDate = new Date(currentTimeInMillis + expires);

        return JWT.create().withAudience(userId)
                .withClaim("userID", userId)
                .withClaim("currentTime", System.currentTimeMillis())
                .withClaim("params", params)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(secret));
    }
    public static String encryptPassword(String password){
        String salt= UUID.randomUUID().toString().replace("-","");
        String finalPassword= DigestUtils.md5DigestAsHex((salt+password).getBytes(StandardCharsets.UTF_8));
        return salt + "$" + finalPassword;
    }

    public static boolean validatePassword(String password,String storedPassword) {
        if (!StringUtils.hasLength(password) || !StringUtils.hasLength(storedPassword)) {
            return false;
        }
        String[] storedPasswordArray = storedPassword.split("\\$");
        if (storedPasswordArray.length != 2) {
            return false;
        }
        String salt = storedPasswordArray[0];
        String storedFinalPassword = storedPasswordArray[1];
        String finalPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes(StandardCharsets.UTF_8));
        return storedFinalPassword.equals(finalPassword);
    }

    public static void verifyToken(String token, String secret) {
        if (org.apache.commons.lang3.StringUtils.isBlank(token) || !token.startsWith("Bearer")) {
            throw new BusinessException(500, "Token is not correctly formatted");
        }
        token = token.substring(7);
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new BusinessException(500, "Verify access token failed:" + e.getMessage());
        }
    }
}
