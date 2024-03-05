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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
