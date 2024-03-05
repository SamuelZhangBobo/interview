package com.align.infrastructure.security;

import com.align.domain.entity.UserEntity;
import com.align.infrastructure.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    @Value("${jwt.accessTokenSecret}")
    private String accessTokenSecret;
    @Value("${jwt.refreshTokenSecret}")
    private String refreshTokenSecret;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("Before processing");
        validateToken(request);
        System.out.println("After processing");
        return true;
    }

    private void validateToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        try{
            System.out.println("During processing");
            UserEntity.verifyToken(token, accessTokenSecret);
        }
        catch(BusinessException accessException){
            try{
                UserEntity.verifyToken(token, refreshTokenSecret);
            } catch (BusinessException refreshException) {
                throw new BusinessException(500, "Token validation failed");
            }
        }

    }
}
