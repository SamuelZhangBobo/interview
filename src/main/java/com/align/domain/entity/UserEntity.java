package com.align.domain.entity;

import com.align.controller.vo.UserDetailVo;
import com.align.domain.config.JwtConfigProperties;
import com.align.domain.config.TokenType;
import com.align.domain.dto.UserDto;
import com.align.domain.repository.UserRepo;
import com.align.infrastructure.exception.BusinessException;
import com.align.infrastructure.po.UserPo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.Resource;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserEntity {

    private UserEntity() {}
    @Resource
    private static UserRepo userRepo;

    @Resource
    private static JwtConfigProperties jwtConfigProperties;
    public static Boolean isUserAccountExists(String username) {
        ArrayList<String> userList = new ArrayList<>();
        userList.add(username);
        List<UserDetailVo> userAccountList = userRepo.getAccount(userList);
        return !userAccountList.isEmpty();
    }

    public static void registerUser(UserDto account) {
        UserPo userAccount = new UserPo();
        userAccount.setUsername(account.getAccountName());
        userAccount.setEmail(account.getEmail());
        userAccount.setPassword(encryptPassword(account.getPassword()));
        userAccount.setToken(generateToken(account.getAccountName(), TokenType.ACCESS_TOKEN, null));
        userAccount.setRefreshToken(generateToken(account.getAccountName(), TokenType.REFRESH_TOKEN, null));
        userRepo.insertAccount(userAccount);
    }

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
        long currentTimeInMillis = System.currentTimeMillis();
        long expires = jwtConfigProperties.getAccessTokenExpireTime() * 60 * 1000; // 30 minutes
        expires = tokenType==TokenType.ACCESS_TOKEN ? expires : expires * 2L;
        String secret = tokenType==TokenType.ACCESS_TOKEN ? jwtConfigProperties.getAccessSecret() : jwtConfigProperties.getRefreshSecret();
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
}
