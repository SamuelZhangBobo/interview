package com.align.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedDto {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "UserName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountName;

    private String content;

    private String postTime;
}
