package com.align.controller;

import com.align.application.services.PostService;
import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;
import com.align.domain.dto.UserDto;
import com.align.infrastructure.exception.BusinessException;
import com.align.infrastructure.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@Tag(name = "Post Management")
public class PostController {
    @Resource
    private PostService postService;

    @PostMapping("/feed-list")
    @Operation(summary = "Get my feed list")
    public GlobalResponse<List<PostDetailVo>> feedList(@RequestBody @Valid UserDto account) throws BusinessException {
        List<PostDetailVo> response = postService.getFeedList(account.getAccountName());
        return GlobalResponse.response(200, response, "Get feed list successfully");
    }

    @PostMapping("/feed")
    @Operation(summary = "Post a feed")
    public GlobalResponse<Boolean> register(@RequestBody @Valid FeedDto feed) throws BusinessException {
        Boolean response = postService.postFeed(feed);
        return GlobalResponse.response(200, response, "Post feed successfully");
    }
}
