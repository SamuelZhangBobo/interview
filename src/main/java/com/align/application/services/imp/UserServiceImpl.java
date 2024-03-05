package com.align.application.services.imp;

import com.align.application.services.UserService;
import com.align.controller.vo.UserDetailVo;
import com.align.domain.dto.UserDto;
import com.align.domain.entity.UserEntity;
import com.align.infrastructure.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public Boolean register(UserDto registry) {
        if(Boolean.TRUE.equals(UserEntity.isUserAccountExists(registry.getAccountName()))) {
            throw new BusinessException(500, "User already registered");
        } else if(Boolean.TRUE.equals(!UserEntity.verifyPasswordRule(registry.getPassword(), registry.getConfirmedPassword()))) {
            String passwordRule = """
                    The password must be a minimum of 8 characters
                    The password must contain at least one uppercase letter
                    The password must contain at least one lowercase letter
                    The password must contain at least one punctuation character
                    """;
            throw new BusinessException(500, passwordRule);
        }
        else {
            UserEntity.registerUser(registry);
            return true;
        }
    }

    @Override
    public UserDetailVo login(UserDto id) {
        return null;
    }
}
