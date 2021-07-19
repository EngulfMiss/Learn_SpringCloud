package com.engulf.springcloud.service;

import com.engulf.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT")  //从哪个服务拿
public interface DeptClientService {
    @GetMapping("/dept/{pid}")
    Dept selectById(@PathVariable("pid") Integer id);

    @GetMapping("/dept/all")
    List<Dept> selectAll();

    @PostMapping("/dept/add")
    int addDept(Dept dept);
}
