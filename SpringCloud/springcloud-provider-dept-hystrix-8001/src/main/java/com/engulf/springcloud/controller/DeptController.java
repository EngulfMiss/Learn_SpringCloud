package com.engulf.springcloud.controller;


import com.engulf.springcloud.pojo.Dept;
import com.engulf.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/{pid}")
    @HystrixCommand(fallbackMethod = "hystrixDeptById")  // 失败就调用备选方法
    public Dept getDeptById(@PathVariable("pid") Integer id){
        Dept dept = deptService.selectDeptById(id);
        if (dept == null){
            throw new RuntimeException("id => "+id+",用户不存在");
        }
        return dept;
    }

    // 备选方案
    public Dept hystrixDeptById(Integer id) {
        Dept dept = new Dept();
        dept.setDeptId(id);
        dept.setDeptName("id => "+id+",用户不存在-- Hystrix");
        dept.setDb_source("no datasource in Mysql");
        return dept;
    }
}
