package com.align.infrastructure.repository.mapper;

import com.align.infrastructure.po.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    UserPo getAccount(@Param("userName") String userName);
    void insertAccount(@Param("account") UserPo account);
    void updateAccount(@Param("account") UserPo account);
    void refreshToken(@Param("userName") String userName,
                     @Param("token") String token,
                     @Param("refreshToken") String refreshToken,
                     @Param("salt") String salt);
}
