package com.align.application.services;

import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;

import java.util.List;

public interface PostService {
    List<PostDetailVo> getFeedList(String userId);
    Boolean postFeed(FeedDto feed);
}
