package com.zk.servcie;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 *
 * Created by zhuk on 2018/3/27.
 * 注意： @RequestParam, @RequestHeader等可以指定参数名称注解，他们的value千万不能少。在SpringMVC程序中，这些注解会根据
 * 参数名来作为默认值，但是在Feign中绑定参数必须通过value属性来知名具体的参数名，不让回抛出IllegalStateException异常，
 * value属性不能为空。
 */
@FeignClient("hello-service")
public interface RefactorHelloService extends com.zk.api.service.HelloService {}
