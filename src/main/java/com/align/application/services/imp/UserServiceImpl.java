package com.align.application.services.imp;

import com.align.application.services.UserService;
import com.align.controller.vo.UserDetailVo;
import com.align.domain.config.TokenProperties;
import com.align.domain.config.TokenType;
import com.align.domain.dto.FollowDto;
import com.align.domain.dto.UserDto;
import com.align.domain.entity.UserEntity;
import com.align.domain.repository.UserRepo;
import com.align.infrastructure.exception.BusinessException;
import com.align.infrastructure.po.UserPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepo userRepo;

    @Resource
    private TokenProperties tokenProperties;
    @Override
    public Boolean register(UserDto registry) {
        UserPo userPo = userRepo.getAccount(registry.getAccountName());
        if(userPo != null) {
            throw new BusinessException(500, "User already registered");
        } else if(Boolean.FALSE.equals(UserEntity.verifyPasswordRule(registry.getPassword(), registry.getConfirmedPassword()))) {
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
        UserPo userPo = userRepo.getAccount(login.getAccountName());
        if(userPo == null){
            throw new BusinessException(500, "User does not exist");
        } else if (!UserEntity.validatePassword(login.getPassword(), userPo.getPassword())) {
            throw new BusinessException(500, "Incorrect password");
        }else{
            updateToken(userPo);
        }
        UserDetailVo userDetail = new UserDetailVo();
        userDetail.setUsername(userPo.getUsername());
        userDetail.setEmail(userPo.getEmail());
        userDetail.setToken(userPo.getToken());
        userDetail.setRefreshToken(userPo.getRefreshToken());
        return userDetail;
    }

    @Override
    public UserDetailVo refreshLogin(UserDto refreshToken) {
        UserPo userPo = userRepo.getAccount(refreshToken.getAccountName());
        updateToken(userPo);
        UserDetailVo userDetail = new UserDetailVo();
        userDetail.setUsername(userPo.getUsername());
        userDetail.setEmail(userPo.getEmail());
        userDetail.setToken(userPo.getToken());
        userDetail.setRefreshToken(userPo.getRefreshToken());
        return userDetail;
    }

    @Override
    public UserDetailVo findUser(String userID) {
        if (userID.isBlank()) {
            throw new BusinessException(500, "Missing userID ID");
        }
        EmailValidator validator = EmailValidator.getInstance();
        UserPo userPo;
        if (validator.isValid(userID)){
            userPo = userRepo.getAccountByEmail(userID);
        } else {
            userPo = userRepo.getAccount(userID);
        }
        UserDetailVo userDetail = new UserDetailVo();
        if (userPo != null) {
            userDetail.setUsername(userPo.getUsername());
            userDetail.setEmail(userPo.getEmail());
        }
        return userDetail;

    }

    @Override
    public Boolean followUsers(FollowDto follow){
        if (!follow.getFollowed().isEmpty()) {
            userRepo.followUsers(follow);
        }
        return true;
    }

    @Override
    public Boolean unfollowUsers(FollowDto unfollow){
        if (!unfollow.getFollowed().isEmpty()) {
            userRepo.unfollowUsers(unfollow);
        }
        return true;
    }

    @Override
    public List<UserDetailVo> listFollowingUsers(String userID){
        List<UserDetailVo> userDetail = new ArrayList<>();
        if (!userID.isEmpty()) {
            userDetail = userRepo.getFollowings(userID);
        }
        return userDetail;
    }

    @Override
    public List<UserDetailVo> listFollowedUsers(String userID){
        List<UserDetailVo> userDetail = new ArrayList<>();
        if (!userID.isEmpty()) {
            userDetail = userRepo.getFollowers(userID);
        }
        return userDetail;
    }

    private void updateToken(UserPo userPo){
        userPo.setToken(UserEntity.generateToken(userPo.getUsername(), TokenType.ACCESS_TOKEN, null, tokenProperties));
        userPo.setRefreshToken(UserEntity.generateToken(userPo.getUsername(), TokenType.REFRESH_TOKEN, null, tokenProperties));
        userRepo.updateAccount(userPo);
    }
    private void registerUser(UserDto account) {
        UserPo userPo = new UserPo();
        userPo.setUsername(account.getAccountName());
        userPo.setEmail(account.getEmail());
        userPo.setPassword(UserEntity.encryptPassword(account.getPassword()));
        userPo.setToken(UserEntity.generateToken(account.getAccountName(), TokenType.ACCESS_TOKEN, null, tokenProperties));
        userPo.setRefreshToken(UserEntity.generateToken(account.getAccountName(), TokenType.REFRESH_TOKEN, null, tokenProperties));
        userRepo.insertAccount(userPo);
    }
}
