package com.zk.controller;

import com.zk.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by zhuk on 2018/3/26.
 */
@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello, host:{}, service_id:{}", instance.getHost(), instance.getServiceId());
        //测试超时
        int sleepTime = 0;
        try {
            sleepTime = new Random().nextInt(3000);
            logger.info("sleepTime : {}", sleepTime);
            Thread.sleep(sleepTime);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = String.format("hello World, server_port = %s, time = %s, sleepTime =%s"
                , port, new Date().toLocaleString(), sleepTime);
        return result;
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.POST)
    public Map testPost(@RequestBody Map<String, String> paramParam, @PathVariable("name") String name) {
        String info = String.format("server_port = %s, name = %s, body = %s", port, name, paramParam);
        logger.info(info);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("name", name);
        result.put("body", paramParam);
        return result;
    }

    //    ============= 添加feign-consumer后代码 ==================
    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "hello " + name;
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public User hello(@RequestHeader String name, @RequestHeader Integer age) {
        return new User(name, age);
    }

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        return String.format("Hello %s, age = %s", user.getName(), user.getAge());
    }
}
