package com.engulf.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {
    @Bean
    //配置负载均衡实现
    @LoadBalanced  //Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
