package com.align.application.services;

import com.align.controller.vo.UserDetailVo;
import com.align.domain.dto.FollowDto;
import com.align.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    Boolean register(UserDto registry);

    UserDetailVo login(UserDto login);

    UserDetailVo refreshLogin(UserDto refreshToken);

    UserDetailVo findUser(String userID);

    Boolean followUsers(FollowDto follow);

    Boolean unfollowUsers(FollowDto unfollow);

    List<UserDetailVo> listFollowingUsers(String userID);

    List<UserDetailVo> listFollowedUsers(String userID);

}
