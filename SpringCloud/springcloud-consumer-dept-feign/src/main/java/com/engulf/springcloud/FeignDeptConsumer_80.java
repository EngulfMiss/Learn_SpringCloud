package com.engulf.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Ribbon 和 Eureka整合以后，客户端可以直接调用，不用关心IP和端口号
@SpringBootApplication(scanBasePackages = "com.engulf.springcloud")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.engulf.springcloud"})
public class FeignDeptConsumer_80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignDeptConsumer_80.class,args);
    }
}
