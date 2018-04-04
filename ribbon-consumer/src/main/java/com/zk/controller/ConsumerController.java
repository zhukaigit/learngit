package com.zk.controller;

import com.zk.servcie.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuk on 2018/3/26.
 */
@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
    public String index() {
        logger.info("消费者api");
        ResponseEntity<String> entity = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class);
        Map<String, String> body = new HashMap<String, String>();
        body.put("k1", "v1");
        body.put("k2", "v2");
        body.put("k3", "v3");
        Map<String, Object> map = new HashMap<String, Object>();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                "http://HELLO-SERVICE/name/{1}", body, Map.class, "zhukai");
//                "http://HELLO-SERVICE/name/{1}", new HttpEntity<>(body), String.class, "zhukai");
        String format = String.format("/hello返回结果：%s\r\n/name/{name}返回结果：%s", entity.getBody(), responseEntity.getBody());
        return format;
    }

    @RequestMapping(value = "/ribbon-consumer/unRetry", method = RequestMethod.GET)
    public String helloConsumer1() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
    }

    @RequestMapping(value = "/ribbon-consumer/retry", method = RequestMethod.GET)
    public String helloConsumer2() {
        return helloService.hello();
    }

}
