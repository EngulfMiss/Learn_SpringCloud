package com.engulf.springcloud.controller;

import com.engulf.springcloud.pojo.Dept;
import com.engulf.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    //获取一些配置的信息，得到具体的微服务
    private DiscoveryClient client;

    @GetMapping("/dept/all")
    public List<Dept> selectAll(){
        return deptService.selectAll();
    }

    @GetMapping("/dept/{pid}")
    public Dept selectDeptById(@PathVariable("pid") Integer id){
        return deptService.selectDeptById(id);
    }

    @PostMapping("/dept/add")
    public int addDept(Dept dept){
        return deptService.addDept(dept);
    }

    @GetMapping("/test")
    public String test(){
        return "This is Test";
    }

    @GetMapping("/discover")
    //注册进来的微服务~，获取一些消息~
    public Object discovery(){
        //获取微服务列表的清单
        List<String> services = client.getServices();
        System.out.println("discovery=>services:"+services);

        //得到具体的微服务信息
        List<ServiceInstance> instances = client.getInstances("SPRINGCLOUD-PROVIDER-DEPT");

        for (ServiceInstance instance : instances) {
            System.out.println(
                instance.getHost()+"\t"+
                instance.getUri()+"\t"+
                instance.getPort()+"\t"+
                instance.getInstanceId()
            );
        }
        return this.client;
    }
}
