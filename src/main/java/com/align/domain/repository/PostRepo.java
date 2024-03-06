package com.align.domain.repository;

import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;

import java.util.List;

public interface PostRepo {
    List<PostDetailVo> getFeedList(String userId);
    void postFeed(FeedDto feed);
}
