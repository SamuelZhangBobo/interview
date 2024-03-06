package com.align.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class FollowDto {
    @NotBlank
    @Size(max = 50)
    private String follower;

    @NotNull
    private List<String> followed;
}
