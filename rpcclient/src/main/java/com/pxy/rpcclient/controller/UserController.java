package com.pxy.rpcclient.controller;

import com.pxy.rpcclient.entity.User;
import com.pxy.rpcclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getUserInfo")
    public String getUserInfo() {
        return userService.getUserInfo(1);
    }
}
