package com.align.application.services;

import com.align.controller.vo.UserDetailVo;
import com.align.domain.dto.UserDto;

public interface UserService {

    Boolean register(UserDto registry);

    UserDetailVo login(UserDto login);

    UserDetailVo refreshLogin(UserDto refreshToken);

    UserDetailVo findUser(String userID);
}
