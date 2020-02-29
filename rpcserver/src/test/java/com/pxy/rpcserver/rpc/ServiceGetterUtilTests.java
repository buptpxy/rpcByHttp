package com.pxy.rpcserver.rpc;

import com.pxy.rpcserver.service.UserServiceImpl;
import com.pxy.rpcserver.utils.ServiceGetterUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceGetterUtilTests {

    UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Test
    public void testGetServiceByClazz() {
        try {
            Object bean = ServiceGetterUtil.getServiceByClazz(UserServiceImpl.class);
            Assert.isInstanceOf(bean.getClass(),userServiceImpl);
        } catch (ServiceGetException e) {
            e.printStackTrace();
        }
    }

}
