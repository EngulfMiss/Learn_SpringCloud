package com.engulf.myrule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngulfRule {
    @Bean
    public IRule myRule(){
        return new EngulfRandomRule();  //使用自定义负载均衡策略
    }
}
