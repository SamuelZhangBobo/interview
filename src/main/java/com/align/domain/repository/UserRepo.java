package com.align.domain.repository;

import com.align.controller.vo.UserDetailVo;
import com.align.domain.dto.FollowDto;
import com.align.infrastructure.po.UserPo;

import java.util.List;

public interface UserRepo {

    UserPo getAccount(String userName);
    UserPo getAccountByEmail(String userEmail);
    void insertAccount(UserPo account);
    void updateAccount(UserPo account);
    void followUsers(FollowDto followMap);
    void unfollowUsers(FollowDto unfollowMap);
    List<UserDetailVo> getFollowings(String userId);
    List<UserDetailVo> getFollowers(String userId);
}
