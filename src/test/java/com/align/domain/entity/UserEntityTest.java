package com.align.domain.entity;

import com.align.domain.config.TokenProperties;
import com.align.domain.config.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest()
class UserEntityTest {

    @Value("${jwt.accessTokenSecret}")
    private String accessTokenSecret;
    @Value("${jwt.refreshTokenSecret}")
    private String refreshTokenSecret;
    @Value("${jwt.accessTokenExpiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiration;

    @Test
    void testVerifyPasswordRule() {
        String password = "Password123!";
        String confirmedPassword = "Password123!";

        boolean result = UserEntity.verifyPasswordRule(password, confirmedPassword);

        assertTrue(result);
    }

    @Test
    void testGenerateToken() {
        TokenProperties tokenProperties = mock(TokenProperties.class);
        when(tokenProperties.getAccessTokenSecret()).thenReturn("accessTokenSecret");
        when(tokenProperties.getRefreshTokenSecret()).thenReturn("refreshTokenSecret");
        when(tokenProperties.getAccessTokenExpiration()).thenReturn(1L);
        when(tokenProperties.getRefreshTokenExpiration()).thenReturn(2L);

        String userId = "userId";
        TokenType tokenType = TokenType.ACCESS_TOKEN;
        HashMap<String, Object> params = new HashMap<>();

        String token = UserEntity.generateToken(userId, tokenType, params, tokenProperties);
        assertEquals(203, token.length());
    }

    @Test
    void testEncryptPassword() {
        String password = "Password123";

        String encryptedPassword = UserEntity.encryptPassword(password);

        assertEquals(65, encryptedPassword.length());
    }

    @Test
    void testValidatePassword() {
        String password = "Password123";
        String encryptedPassword = UserEntity.encryptPassword(password);

        boolean result = UserEntity.validatePassword(password, encryptedPassword);

        assertTrue(result);
    }

    @Test
    void testVerifyToken() {
        TokenProperties tokenProperties = new TokenProperties();
        tokenProperties.setAccessTokenSecret(accessTokenSecret);
        tokenProperties.setRefreshTokenSecret(refreshTokenSecret);
        tokenProperties.setAccessTokenExpiration(accessTokenExpiration);
        tokenProperties.setRefreshTokenExpiration(refreshTokenExpiration);

        String token = UserEntity.generateToken("userId", TokenType.ACCESS_TOKEN, null, tokenProperties);
        String BearerToken = "Bearer " + token;
        String secret = accessTokenSecret;

        UserEntity.verifyToken(BearerToken, secret);
    }
}
