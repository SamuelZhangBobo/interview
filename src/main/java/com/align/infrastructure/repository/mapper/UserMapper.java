package com.align.infrastructure.repository.mapper;

import com.align.controller.vo.UserDetailVo;
import com.align.infrastructure.po.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {

    UserPo getAccount(@Param("userName") String userName);
    UserPo getAccountByEmail(@Param("emailAddress") String emailAddress);
    void insertAccount(@Param("account") UserPo account);
    void updateAccount(@Param("account") UserPo account);
    void followUsers(@Param("follower") String follower, @Param("followed") List<String> followed);
    void unfollowUsers(@Param("follower") String follower, @Param("unfollowed") List<String> unfollowed);
    List<UserDetailVo> getFollowings(@Param("userId") String userId);
    List<UserDetailVo> getFollowers(@Param("userId") String userId);
}
