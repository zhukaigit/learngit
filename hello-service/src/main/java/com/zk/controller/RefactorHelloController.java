package com.zk.controller;

import com.zk.api.model.User;
import com.zk.api.service.HelloService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuk on 2018/3/26.
 * 注意：在Controller中不再包含以往会定义的请求映射注解@RequestMapping，而参数的注解定义在重写的时候会自动带过来。
 *      在这个类中，除了要实现接口逻辑之外，只需要增加@RestController注解使该类成为一个REST接口类就大功告成了。
 */
@RestController
public class RefactorHelloController implements HelloService {

    @Value("${server.port}")
    private String port;

    @Override
    public String hello(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @Override
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return new User(name, age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return String.format("Hello %s, age = %s", user.getName(), user.getAge());
    }
}
