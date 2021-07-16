package com.engulf.springcloud.service;

import com.engulf.springcloud.mapper.DeptMapper;
import com.engulf.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper mapper;

    @Override
    public List<Dept> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public Dept selectDeptById(Integer id) {
        return mapper.selectDeptById(id);
    }

    @Override
    public boolean addDept(Dept dept) {
        return mapper.addDept(dept);
    }
}
