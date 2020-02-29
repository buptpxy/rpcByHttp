package com.pxy.rpcserver.rpc;

import com.alibaba.fastjson.JSON;
import com.pxy.rpcserver.entity.User;
import com.pxy.rpcserver.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MainControllerTests {
    UserServiceImpl userServiceImpl = new UserServiceImpl();
    @Autowired
    MainController mainController;
    @Test
    public void testGetResult() {

    }

}
