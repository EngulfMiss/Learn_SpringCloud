package com.engulf.springcloud.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//使用分布式，实体类需要序列化
@Data
@NoArgsConstructor
public class Dept implements Serializable {    // Dept 实体类 orm 对象关系映射
    private Integer deptId;
    private String deptName;

    //判断数据是存在哪个数据库的字段  微服务的特性  一个服务可以对应一个数据库，同一个信息也可能存在不同的数据库
    private String db_source;

    public Dept(String deptName) {
        this.deptName = deptName;
    }
}
