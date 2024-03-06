package com.align.controller;

import com.align.application.services.UserService;
import com.align.infrastructure.response.GlobalResponse;
import com.align.controller.vo.UserDetailVo;
import com.align.domain.dto.UserDto;
import com.align.infrastructure.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "User Management")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Add new user")
    public GlobalResponse<Boolean> register(@RequestBody @Valid UserDto registry) throws BusinessException {
        Boolean response = userService.register(registry);
        return GlobalResponse.response(200, response, "Register successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "User login")
    public GlobalResponse<UserDetailVo> login(@RequestBody @Valid UserDto login) {
        UserDetailVo response = userService.login(login);
        return GlobalResponse.response(200, response, "Login successfully");
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh user login with refresh token")
    public GlobalResponse<UserDetailVo> refreshLogin(@RequestBody @Valid UserDto refreshToken) {
        UserDetailVo response = userService.refreshLogin(refreshToken);
        return GlobalResponse.response(200, response, "Refresh login successfully");
    }

    @GetMapping("/retrieve")
    @Operation(summary = "Search user by username or email")
    public GlobalResponse<UserDetailVo> findUser(@RequestBody @Valid UserDto userAccount) {
        UserDetailVo response = userService.findUser(userAccount.getAccountName());
        return GlobalResponse.response(200, response, "Retrieve user successfully");
    }
}
