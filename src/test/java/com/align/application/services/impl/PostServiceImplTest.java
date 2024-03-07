package com.align.application.services.impl;

import com.align.application.services.imp.PostServiceImpl;
import com.align.controller.vo.PostDetailVo;
import com.align.domain.dto.FeedDto;
import com.align.domain.repository.PostRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void testGetFeedList() {
        String userId = "123";
        List<PostDetailVo> expectedPostDetail = new ArrayList<>();
        when(postRepo.getFeedList(userId)).thenReturn(expectedPostDetail);

        List<PostDetailVo> actualPostDetail = postService.getFeedList(userId);

        assertEquals(expectedPostDetail, actualPostDetail);
        verify(postRepo, times(1)).getFeedList(userId);
    }

    @Test
    void testPostFeed() {
        FeedDto feed = new FeedDto();
        feed.setAccountName("test");

        boolean expectedResult = true;

        boolean actualResult = postService.postFeed(feed);

        assertEquals(expectedResult, actualResult);
        verify(postRepo, times(1)).postFeed(feed);
    }
}
