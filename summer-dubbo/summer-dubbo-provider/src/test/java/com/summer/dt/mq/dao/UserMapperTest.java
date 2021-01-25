package com.summer.dt.mq.dao;

import com.summer.dubbo.DubboProviderApplication;
import com.summer.dubbo.provider.entity.User;
import com.summer.dubbo.provider.dao.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DubboProviderApplication.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void save() {
        User user = new User().setAge(3).setCreateTime(new Date())
                    .setName("lw").setSex(User.Sex.MAN);
        userMapper.save(user);
        Assert.assertNotNull(user);
    }

}