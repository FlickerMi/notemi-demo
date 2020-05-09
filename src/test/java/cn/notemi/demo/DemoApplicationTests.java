package cn.notemi.demo;

import cn.notemi.demo.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() {

    }

    @Test
    public void get() {
        System.out.println("123: " + redisService.get("123"));
    }

}
