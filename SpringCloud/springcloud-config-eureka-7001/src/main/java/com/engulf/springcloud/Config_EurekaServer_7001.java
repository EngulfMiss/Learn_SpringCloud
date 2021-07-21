package com.engulf.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  // @EnableEurekaServer  服务端的启动类，可以接受别人注册进来
public class Config_EurekaServer_7001 {
    public static void main(String[] args) {
        SpringApplication.run(Config_EurekaServer_7001.class,args);
    }
}
