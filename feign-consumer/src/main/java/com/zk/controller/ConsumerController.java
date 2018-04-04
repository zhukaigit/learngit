package com.zk.controller;

import com.zk.model.User;
import com.zk.servcie.HelloService;
import com.zk.servcie.RefactorHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuk on 2018/3/26.
 */
@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private HelloService helloService;
    @Autowired
    private RefactorHelloService refactorHelloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        logger.info("feign消费者api");
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String helloConsumer2() {
        logger.info("feign消费者api2");
        StringBuilder sb = new StringBuilder();
        sb.append(helloService.hello()).append("\n");
        sb.append(helloService.hello("DIDI")).append("\n");
        sb.append(helloService.hello("DIDI", 30)).append("\n");
        sb.append(helloService.hello(new User("DIDI", 30))).append("\n");
        return sb.toString();
    }

    @RequestMapping(value = "/feign-consumer3", method = RequestMethod.GET)
    public String helloConsumer3() {
        logger.info("feign消费者api3");
        StringBuilder sb = new StringBuilder();
        sb.append(refactorHelloService.hello("DIDI")).append("\n");
        sb.append(refactorHelloService.hello("DIDI", 30)).append("\n");
        sb.append(refactorHelloService.hello(new com.zk.api.model.User("DIDI", 30))).append("\n");
        return sb.toString();
    }

}
