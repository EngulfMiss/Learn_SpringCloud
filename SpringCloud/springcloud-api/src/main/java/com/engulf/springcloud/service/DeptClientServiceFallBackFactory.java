package com.engulf.springcloud.service;

import com.engulf.springcloud.pojo.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

// 服务降级
@Component
public class DeptClientServiceFallBackFactory implements FallbackFactory {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept selectById(Integer id) {
                Dept dept = new Dept();
                dept.setDeptId(id);
                dept.setDeptName("id => "+id+"没有对应的信息，客户端提供了降级的信息，这个服务现在已经关闭");
                dept.setDb_source("没有数据~");
                return dept;
            }

            @Override
            public List<Dept> selectAll() {
                return null;
            }

            @Override
            public int addDept(Dept dept) {
                return 0;
            }
        };
    }
}
