package com.align.application.services.imp;

import com.align.application.services.PostService;
import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;
import com.align.domain.repository.PostRepo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostRepo postRepo;

    @Override
    public List<PostDetailVo> getFeedList(String userId){
        List<PostDetailVo> postDetail = new ArrayList<>();
        if (!userId.isEmpty()) {
            postDetail = postRepo.getFeedList(userId);
        }
        return postDetail;
    }

    @Override
    public Boolean postFeed(FeedDto feed){
        if (!feed.getAccountName().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(new Date());
            feed.setPostTime(currentTime);
            postRepo.postFeed(feed);
        }
        return true;
    }
}
