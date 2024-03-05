package com.align.infrastructure.repository.mapper;

import com.align.controller.vo.UserDetailVo;
import com.align.infrastructure.po.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserDetailVo> getAccount(@Param("userName") List<String> userName);
    void insertAccount(@Param("account") UserPo account);
    void refreshToken(@Param("userName") String userName,
                     @Param("token") String token,
                     @Param("refreshToken") String refreshToken,
                     @Param("salt") String salt);
}
