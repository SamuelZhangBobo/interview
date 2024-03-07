package com.align.application.services.impl;

import com.align.application.services.imp.UserServiceImpl;
import com.align.controller.vo.UserDetailVo;
import com.align.domain.config.TokenProperties;
import com.align.domain.dto.FollowDto;
import com.align.domain.dto.UserDto;
import com.align.domain.entity.UserEntity;
import com.align.domain.repository.UserRepo;
import com.align.infrastructure.po.UserPo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest()
class UserServiceImplTest {

    @Value("${jwt.accessTokenSecret}")
    private String accessTokenSecret;
    @Value("${jwt.refreshTokenSecret}")
    private String refreshTokenSecret;
    @Value("${jwt.accessTokenExpiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiration;
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private TokenProperties mockTokenProperties;

    @Test
    void testRegister() {
        UserDto registry = new UserDto();
        registry.setAccountName("test");
        registry.setPassword("Password123!");
        registry.setConfirmedPassword("Password123!");

        when(userRepo.getAccount(registry.getAccountName())).thenReturn(null);
        TokenProperties tokenProperties = new TokenProperties();
        tokenProperties.setAccessTokenSecret(accessTokenSecret);
        tokenProperties.setRefreshTokenSecret(refreshTokenSecret);
        tokenProperties.setAccessTokenExpiration(accessTokenExpiration);
        tokenProperties.setRefreshTokenExpiration(refreshTokenExpiration);
        when(mockTokenProperties.getAccessTokenSecret()).thenReturn(tokenProperties.getAccessTokenSecret());
        when(mockTokenProperties.getRefreshTokenSecret()).thenReturn(tokenProperties.getRefreshTokenSecret());
        when(mockTokenProperties.getAccessTokenExpiration()).thenReturn(1L);
        when(mockTokenProperties.getRefreshTokenExpiration()).thenReturn(2L);

        boolean result = userService.register(registry);

        verify(userRepo, times(1)).insertAccount(any(UserPo.class));
    }

    @Test
    void testLogin() {
        UserDto login = new UserDto();
        login.setAccountName("test");
        login.setPassword("Password123!");

        UserPo userPo = new UserPo();
        userPo.setUsername("test");
        userPo.setPassword(UserEntity.encryptPassword("Password123!"));

        when(userRepo.getAccount(login.getAccountName())).thenReturn(userPo);
        TokenProperties tokenProperties = new TokenProperties();
        tokenProperties.setAccessTokenSecret(accessTokenSecret);
        tokenProperties.setRefreshTokenSecret(refreshTokenSecret);
        tokenProperties.setAccessTokenExpiration(accessTokenExpiration);
        tokenProperties.setRefreshTokenExpiration(refreshTokenExpiration);
        when(mockTokenProperties.getAccessTokenSecret()).thenReturn(tokenProperties.getAccessTokenSecret());
        when(mockTokenProperties.getRefreshTokenSecret()).thenReturn(tokenProperties.getRefreshTokenSecret());
        when(mockTokenProperties.getAccessTokenExpiration()).thenReturn(1L);
        when(mockTokenProperties.getRefreshTokenExpiration()).thenReturn(2L);

        UserDetailVo userDetail = userService.login(login);

        assertEquals("test", userDetail.getUsername());
        verify(userRepo, times(1)).getAccount(login.getAccountName());
        verify(userRepo, times(1)).updateAccount(any(UserPo.class));
    }

    @Test
    void testRefreshLogin() {
        UserDto refreshToken = new UserDto();
        refreshToken.setAccountName("test");

        UserPo userPo = new UserPo();
        userPo.setUsername("test");
        userPo.setEmail("test@test.com");
        userPo.setToken("mockToken");
        userPo.setRefreshToken("mockRefreshToken");

        when(userRepo.getAccount(refreshToken.getAccountName())).thenReturn(userPo);
        TokenProperties tokenProperties = new TokenProperties();
        tokenProperties.setAccessTokenSecret(accessTokenSecret);
        tokenProperties.setRefreshTokenSecret(refreshTokenSecret);
        tokenProperties.setAccessTokenExpiration(accessTokenExpiration);
        tokenProperties.setRefreshTokenExpiration(refreshTokenExpiration);
        when(mockTokenProperties.getAccessTokenSecret()).thenReturn(tokenProperties.getAccessTokenSecret());
        when(mockTokenProperties.getRefreshTokenSecret()).thenReturn(tokenProperties.getRefreshTokenSecret());
        when(mockTokenProperties.getAccessTokenExpiration()).thenReturn(1L);
        when(mockTokenProperties.getRefreshTokenExpiration()).thenReturn(2L);

        UserDetailVo userDetail = userService.refreshLogin(refreshToken);

        assertEquals("test", userDetail.getUsername());
        assertEquals("test@test.com", userDetail.getEmail());
        verify(userRepo, times(1)).getAccount(refreshToken.getAccountName());
        verify(userRepo, times(1)).updateAccount(any(UserPo.class));
    }

    @Test
    void testFindUser() {
        String userID = "test";
        UserPo userPo = new UserPo();
        userPo.setUsername("test");
        userPo.setEmail("test@test.com");

        when(userRepo.getAccount(userID)).thenReturn(userPo);

        UserDetailVo userDetail = userService.findUser(userID);

        assertEquals("test", userDetail.getUsername());
        assertEquals("test@test.com", userDetail.getEmail());
        verify(userRepo, times(1)).getAccount(userID);
    }

    @Test
    void testFindUserByEmail() {
        String userID = "test@test.com";
        UserPo userPo = new UserPo();
        userPo.setUsername("test");
        userPo.setEmail("test@test.com");

        when(userRepo.getAccountByEmail(userPo.getEmail())).thenReturn(userPo);

        UserDetailVo userDetail = userService.findUser(userID);

        assertEquals("test", userDetail.getUsername());
        assertEquals("test@test.com", userDetail.getEmail());
        verify(userRepo, times(1)).getAccountByEmail(userPo.getEmail());
    }

    @Test
    void testFollowUsers() {
        FollowDto follow = new FollowDto();
        follow.setFollowed(List.of("user1", "user2"));

        boolean result = userService.followUsers(follow);

        assertTrue(result);
        verify(userRepo, times(1)).followUsers(follow);
    }

    @Test
    void testUnfollowUsers() {
        FollowDto unfollow = new FollowDto();
        unfollow.setFollowed(List.of("user1", "user2"));

        boolean result = userService.unfollowUsers(unfollow);

        assertTrue(result);
        verify(userRepo, times(1)).unfollowUsers(unfollow);
    }

    @Test
    void testListFollowingUsers() {
        String userID = "test";
        List<UserDetailVo> userDetails = new ArrayList<>();
        userDetails.add(new UserDetailVo());

        when(userRepo.getFollowings(userID)).thenReturn(userDetails);

        List<UserDetailVo> result = userService.listFollowingUsers(userID);

        assertEquals(userDetails, result);
        verify(userRepo, times(1)).getFollowings(userID);
    }

    @Test
    void testListFollowedUsers() {
        String userID = "test";
        List<UserDetailVo> userDetails = new ArrayList<>();
        userDetails.add(new UserDetailVo());

        when(userRepo.getFollowers(userID)).thenReturn(userDetails);

        List<UserDetailVo> result = userService.listFollowedUsers(userID);

        assertEquals(userDetails, result);
        verify(userRepo, times(1)).getFollowers(userID);
    }
}
