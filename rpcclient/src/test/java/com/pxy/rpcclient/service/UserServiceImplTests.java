package com.pxy.rpcclient.service;

import com.pxy.rpcclient.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTests {
    @Autowired
    UserService userService;
    @Test
    public void testGetUserInfo() {
        String userInfo = userService.getUserInfo(1);
    }
}
