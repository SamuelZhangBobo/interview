package com.align.domain.repository;

import com.align.controller.vo.UserDetailVo;
import com.align.infrastructure.po.UserPo;

import java.util.List;

public interface UserRepo {

    List<UserDetailVo> getAccount(List<String> userName);
    void insertAccount(UserPo account);
    void refreshToken(String userName, String token, String refreshToken, String salt);
}
