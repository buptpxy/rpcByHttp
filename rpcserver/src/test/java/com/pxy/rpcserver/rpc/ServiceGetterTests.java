package com.pxy.rpcserver.rpc;

import com.pxy.rpcserver.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceGetterTests {

    UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Test
    public void testGetServiceByClazz() {
        try {
            Object bean = ServiceGetter.getServiceByClazz(UserServiceImpl.class);
            Assert.isInstanceOf(bean.getClass(),userServiceImpl);
        } catch (ServiceGetException e) {
            e.printStackTrace();
        }
    }

}
