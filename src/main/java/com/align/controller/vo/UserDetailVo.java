package com.align.controller.vo;

import lombok.Data;

@Data
public class UserDetailVo {

    private String username;

    private String email;

    private String token;

    private String refreshToken;
}
