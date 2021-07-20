# Hystrix
## 什么是Hystrix?
- Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免地会调用失败，比如超时，异常等，
Hystrix能保证在一个依赖出问题地情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统地弹性。  
- “断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器地故障监控(类似熔断保险丝)，**向调用方返回一个服务预  
期的，可处理的备选响应(FallBack)，而不是长时间的等待或者抛出调用方法无法处理的异常，这样就可以保证服务调用方的线程  
不会被长时间，不必要的占用**，从而避免了故障在分布式系统中的蔓延，乃至雪崩。

## 服务雪崩
- 多个服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其他的微服务，这就是所谓的“扇出”，如果扇出的
链路上某个微服务的调用响应时间过长或不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”。  

## Hystrix能干嘛
- 服务降级
- 服务熔断
- 服务限流
- 接近实时的监控

### 服务熔断
熔断机制是对应雪崩效应的一种微服务链路保护机制。  
当扇出链路的某个微服务不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。当检测到  
该节点微服务调用响应正常后恢复调用链路。在SpringCloud框架里熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，当失败  
的调用到一定阈值，缺省是5秒内20次调用失败就会启动熔断机制。熔断机制的注解是@HystrixCommand。  
  
## Hystrix使用
### 服务熔断--(服务端)
- 导入依赖
```xml
<!-- Hystrix -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

- 编写Controoler，设置备选方案 @HystrixCommand(fallbackMethod = methodName)
```java
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/dept/{pid}")
    @HystrixCommand(fallbackMethod = "hystrix_getDeptById")  // 失败就调用备选方法
    public Dept getDeptById(@PathVariable("pid") Integer id){
        Dept dept = deptService.selectDeptById(id);
        if (dept == null){
            throw new RuntimeException("id => "+id+",用户不存在");
        }
        return dept;
    }

    // 备选方案
    @GetMapping("/dept/{pid}")
    public Dept hystrix_getDeptById(@PathVariable("pid") Integer id) {
        Dept dept = new Dept();
        dept.setDeptId(id);
        dept.setDeptName("id => "+id+",用户不存在-- Hystrix");
        dept.setDb_source("no datasource in Mysql");
        return dept;
    }
}
```

- 开启熔断器支持
```java
@SpringBootApplication()
@EnableEurekaClient  //在服务启动后自动注册到Eureka中
@EnableDiscoveryClient
//添加对熔断的支持
@EnableHystrix
public class HystrixDeptProvider_8001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDeptProvider_8001.class,args);
    }
}
```

### 服务降级--(客户端)
- 编写一个降级处理的类(实现FallbackFactory接口)
```java
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
```

- 在feign中指定服务降级的类@FeignClient(fallbackFactory = Xxx.class)
```java
@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT",fallbackFactory = DeptClientServiceFallBackFactory.class)  //从哪个服务拿 //指定服务降级
public interface DeptClientService {
    @GetMapping("/dept/{pid}")
    Dept selectById(@PathVariable("pid") Integer id);

    @GetMapping("/dept/all")
    List<Dept> selectAll();

    @PostMapping("/dept/add")
    int addDept(Dept dept);
}

- 在客户端配置文件中开启降级
```yml
# 开启降级 feign.hystrix
feign:
  hystrix:
    enabled: true
```

## 服务熔断与服务降级
服务熔断：服务端，某个服务超时或者异常，启用备用解决方案  
服务降级：客户端，从整体网站请求负载考虑，当某个服务熔断或者被关闭之后，服务将不再被调用  
此时在客户端，我们可以准备一个FallbackFactory，返回一个默认值(缺省值)


## Hystrix客户端监控
