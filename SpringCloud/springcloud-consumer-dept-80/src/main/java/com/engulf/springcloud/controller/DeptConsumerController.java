package com.engulf.springcloud.controller;

import com.engulf.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptConsumerController {
    //消费者是不应该有service层的，那么如何请求service服务?(Restful请求)
    //RestFul   RestTemplate...   供我们直接调用
    // getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
    @Autowired
    private RestTemplate restTemplate;  //提供多种便捷访问远程http服务的方法，简单的restful服务模板

    //请求服务地址前缀常量
//    private static final String staticUrl = "http://localhost:8001";
    //但如果使用Ribbon，我们这里的地址就应该使用的是一个变量，通过服务名来访问
    private String staticUrl = "http://SPRINGCLOUD-PROVIDER-DEPT";

    @GetMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Integer id){
        return restTemplate.getForObject(staticUrl+"/dept/"+id,Dept.class);
    }

    @GetMapping("/consumer/dept/list")
    public List<Dept> getAll(){
        return restTemplate.getForObject(staticUrl+"/dept/all",List.class);
    }

    @RequestMapping("/consumer/dept/add")
    public boolean addDept(Dept dept){
        return restTemplate.postForObject(staticUrl+"/dept/add",dept,Boolean.class);
    }

    @GetMapping("/test80")
    public String test_80(){
        return restTemplate.getForObject(staticUrl+"/test",String.class);
    }


}
