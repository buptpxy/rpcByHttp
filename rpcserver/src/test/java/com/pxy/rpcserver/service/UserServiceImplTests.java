package com.pxy.rpcserver.service;

import com.pxy.rpcserver.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTests {
    UserService userService = new UserServiceImpl();
    @Test
    public void testGetUserInfo() {
        String userInfo = userService.getUserInfo(1);

    }
}
