package com.align.domain.repository;

import com.align.infrastructure.po.UserPo;

public interface UserRepo {

    UserPo getAccount(String userName);
    void insertAccount(UserPo account);
    void updateAccount(UserPo account);
    void refreshToken(String userName, String token, String refreshToken, String salt);
}
