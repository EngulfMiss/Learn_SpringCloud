package com.engulf.springcloud.service;

import com.engulf.springcloud.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> selectAll();

    Dept selectDeptById(Integer id);

    boolean addDept(Dept dept);
}
