package com.align.infrastructure.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPo extends BasePo{

    private String username;

    private String email;

    private String password;

    private String token;

    private String refreshToken;
}
