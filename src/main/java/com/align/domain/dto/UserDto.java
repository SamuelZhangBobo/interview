package com.align.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "UserName or Email for login", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountName;

    @Email
    private String email;

    @Schema(description = "Required for login", requiredMode = Schema.RequiredMode.AUTO)
    private String password;

    private String confirmedPassword;
}