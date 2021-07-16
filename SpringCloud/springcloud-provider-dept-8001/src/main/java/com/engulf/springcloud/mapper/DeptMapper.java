package com.engulf.springcloud.mapper;

import com.engulf.springcloud.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeptMapper {
    List<Dept> selectAll();

    Dept selectDeptById(Integer id);

    boolean addDept(Dept dept);
}
