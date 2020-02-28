//此类迁移到了rpcserver中
//package com.pxy.rpcclient.service;
//
//import com.pxy.rpcclient.entity.User;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Override
//    public String getUserInfo(int id) {
//        User user = setUser(id);
//        return user.toString();
//    }
//
//    private User setUser(int id) {
//        User user = new User();
//        user.setId(id);
//        user.setAge(18);
//        user.setName("madongmei");
//        return user;
//    }
//
//}
