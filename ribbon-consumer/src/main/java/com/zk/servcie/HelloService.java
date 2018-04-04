package com.zk.servcie;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhuk on 2018/3/27.
 */
@Service
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String hello() {
        long start = System.currentTimeMillis();
        String body = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
        long end = System.currentTimeMillis();
        logger.info("Spend time : {}", (end - start));
        return body;
    }

    public String helloFallback() {
        return "error";
    }
}
