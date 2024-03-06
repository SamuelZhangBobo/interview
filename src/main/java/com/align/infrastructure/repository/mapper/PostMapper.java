package com.align.infrastructure.repository.mapper;

import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<PostDetailVo> getFeedList(@Param("userId") String userId);
    void postFeed(@Param("feed") FeedDto feed);
}
