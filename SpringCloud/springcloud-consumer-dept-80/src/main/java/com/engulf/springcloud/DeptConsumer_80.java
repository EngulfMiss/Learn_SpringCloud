package com.engulf.springcloud;

import com.engulf.myrule.EngulfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

//Ribbon 和 Eureka整合以后，客户端可以直接调用，不用关心IP和端口号
@SpringBootApplication
@EnableEurekaClient
//在微服务启动的时候就能去加载我们自定义负载均衡类(IRule)
@RibbonClient(name = "SPRINGCLOUD-PROVIDER-DEPT",configuration = EngulfRule.class)  //name对哪个服务进行负载均衡,指定配置类
public class DeptConsumer_80 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_80.class,args);
    }
}
