package com.engulf.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {
    @Bean
    //配置负载均衡实现
    /*IRule  负载均衡的核心实现 IRule接口
    *   RoundRobinRule：轮询
    *   AvailabilityFilteringRule：会先过滤掉崩溃的服务，对剩下的进行轮询
    *   RandomRule：随机
    *   WeightedResponseTimeRule：权重
    *   RetryRule：重试-会先按照轮询获取服务，如果服务获取失败。则会在指定的时间内进行重试
    * */
    @LoadBalanced  //Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
