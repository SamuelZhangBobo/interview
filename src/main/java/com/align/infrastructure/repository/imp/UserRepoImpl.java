package com.align.infrastructure.repository.imp;

import com.align.controller.vo.UserDetailVo;
import com.align.domain.dto.FollowDto;
import com.align.domain.repository.UserRepo;
import com.align.infrastructure.po.UserPo;
import com.align.infrastructure.repository.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepoImpl implements UserRepo {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserPo getAccount(String userName) {
        return userMapper.getAccount(userName);
    }

    @Override
    public UserPo getAccountByEmail(String userEmail) {return userMapper.getAccountByEmail(userEmail);}

    @Override
    public void insertAccount(UserPo account) {
        userMapper.insertAccount(account);
    }

    @Override
    public void updateAccount(UserPo account) {
        userMapper.updateAccount(account);
    }

    @Override
    public void followUsers(FollowDto followMap) {
        userMapper.followUsers(followMap.getFollower(), followMap.getFollowed());
    }
    @Override
    public void unfollowUsers(FollowDto unfollowMap) {
        userMapper.unfollowUsers(unfollowMap.getFollower(), unfollowMap.getFollowed());
    }
    @Override
    public List<UserDetailVo> getFollowings(String userId){
        return userMapper.getFollowings(userId);
    }
    @Override
    public List<UserDetailVo> getFollowers(String userId){
       return userMapper.getFollowers(userId);
    }
}
