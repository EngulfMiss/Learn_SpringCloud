package com.engulf.springcloud.controller;

import com.engulf.springcloud.pojo.Dept;
import com.engulf.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/all")
    public List<Dept> selectAll(){
        return deptService.selectAll();
    }

    @GetMapping("/dept/{pid}")
    public Dept selectDeptById(@PathVariable("pid") Integer id){
        return deptService.selectDeptById(id);
    }

    @PostMapping("/dept/add")
    public boolean addDept(Dept dept){
        return deptService.addDept(dept);
    }
}
