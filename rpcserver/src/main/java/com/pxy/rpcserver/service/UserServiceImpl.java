package com.pxy.rpcserver.service;

import com.pxy.rpcserver.entity.User;

public class UserServiceImpl implements UserService{
    /**
     * 注意如果此处返回的是User类型，则与rpcclient的User类型不同，在rpcclient中只有com.pxy.rpcclient.entity.User类型，找不到com.pxy.rpcserver.entity.User类型会报错
     * @param id
     * @return
     */
    public String getUserInfo(int id) {
        User user = setUser(id);
        return user.toString();
    }

    private User setUser(int id) {
        User user = new User();
        user.setId(id);
        user.setAge(18);
        user.setName("madongmei");
        return user;
    }

}
