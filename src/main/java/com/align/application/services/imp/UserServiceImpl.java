package com.align.application.services.imp;

import com.align.application.services.UserService;
import com.align.controller.vo.UserDetailVo;
import com.align.domain.config.TokenType;
import com.align.domain.dto.UserDto;
import com.align.domain.entity.UserEntity;
import com.align.domain.repository.UserRepo;
import com.align.infrastructure.exception.BusinessException;
import com.align.infrastructure.po.UserPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepo userRepo;
    @Override
    public Boolean register(UserDto registry) {
        UserPo userAccount = userRepo.getAccount(registry.getAccountName());
        if(userAccount != null) {
            throw new BusinessException(500, "User already registered");
        } else if(Boolean.TRUE.equals(UserEntity.verifyPasswordRule(registry.getPassword(), registry.getConfirmedPassword()))) {
            String passwordRule = """
                    The password must be a minimum of 8 characters
                    The password must contain at least one uppercase letter
                    The password must contain at least one lowercase letter
                    The password must contain at least one punctuation character
                    """;
            throw new BusinessException(500, passwordRule);
        }
        else {
            registerUser(registry);
            return true;
        }
    }
    @Override
    public UserDetailVo login(UserDto login) {
        UserPo userAccount = userRepo.getAccount(login.getAccountName());
        if(userAccount == null){
            throw new BusinessException(500, "User does not exist");
        } else if (!UserEntity.validatePassword(login.getPassword(), userAccount.getPassword())) {
            throw new BusinessException(500, "Incorrect password");
        }else{
            userAccount.setToken(UserEntity.generateToken(userAccount.getUsername(), TokenType.ACCESS_TOKEN, null));
            userAccount.setRefreshToken(UserEntity.generateToken(userAccount.getUsername(), TokenType.REFRESH_TOKEN, null));
            userRepo.updateAccount(userAccount);
        }
        UserDetailVo userDetail = new UserDetailVo();
        userDetail.setUsername(userAccount.getUsername());
        userDetail.setEmail(userAccount.getEmail());
        userDetail.setToken(userAccount.getToken());
        userDetail.setRefreshToken(userAccount.getRefreshToken());
        return userDetail;
    }

    private void registerUser(UserDto account) {
        UserPo userAccount = new UserPo();
        userAccount.setUsername(account.getAccountName());
        userAccount.setEmail(account.getEmail());
        userAccount.setPassword(UserEntity.encryptPassword(account.getPassword()));
        userAccount.setToken(UserEntity.generateToken(account.getAccountName(), TokenType.ACCESS_TOKEN, null));
        userAccount.setRefreshToken(UserEntity.generateToken(account.getAccountName(), TokenType.REFRESH_TOKEN, null));
        userRepo.insertAccount(userAccount);
    }
}
