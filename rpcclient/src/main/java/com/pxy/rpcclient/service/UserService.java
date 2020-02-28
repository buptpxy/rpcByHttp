package com.pxy.rpcclient.service;

import com.pxy.rpcclient.entity.User;
import com.pxy.rpcclient.proxy.RemoteClass;

@RemoteClass("com.pxy.rpcserver.service.UserServiceImpl")
public interface UserService {
    String getUserInfo(int id);
}
