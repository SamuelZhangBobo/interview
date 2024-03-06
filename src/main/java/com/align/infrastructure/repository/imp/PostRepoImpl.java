package com.align.infrastructure.repository.imp;

import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;
import com.align.domain.repository.PostRepo;
import com.align.infrastructure.repository.mapper.PostMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostRepoImpl implements PostRepo {
    @Resource
    private PostMapper postMapper;
    @Override
    public List<PostDetailVo> getFeedList(String userId){
        return postMapper.getFeedList(userId);
    }
    @Override
    public void postFeed(FeedDto feed){
        postMapper.postFeed(feed);
    }
}
