package com.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@SpringCloudApplication	//相当于上面三个注解
public class AppStart {
	public static void main(String[] args) {
		SpringApplication.run(AppStart.class, args);
	}
}
