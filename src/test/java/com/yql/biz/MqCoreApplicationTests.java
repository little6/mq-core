package com.yql.biz;

import com.yql.biz.producer.YqlProducer;
import com.yql.biz.web.MQBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqCoreApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testApi() {
        MQBuilder.sendMessage(new YqlProducer("TEST_USER_REGISTER", "PID_YQL_USER_REGISTER", "UserRegisterTag", "user_register", "hello  world"));
    }
}
