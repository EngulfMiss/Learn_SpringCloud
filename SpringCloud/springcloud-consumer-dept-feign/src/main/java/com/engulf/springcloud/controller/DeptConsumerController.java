package com.engulf.springcloud.controller;

import com.engulf.springcloud.pojo.Dept;
import com.engulf.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptConsumerController {


    @Autowired
    private DeptClientService service = null;

    @GetMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Integer id){
        return service.selectById(id);
    }

    @GetMapping("/consumer/dept/list")
    public List<Dept> getAll(){
        return service.selectAll();
    }

    @RequestMapping("/consumer/dept/add")
    public int addDept(Dept dept){
        return service.addDept(dept);
    }

}
