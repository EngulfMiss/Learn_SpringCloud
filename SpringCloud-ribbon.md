### ribbon是什么?  
- Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
- 简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，  
将NetFlix的中间层服务连接在一起。Ribbon的客户端组件提供一系列完整的配置项如:连接超时、重试等等。  
简单的说，就是在配置文件中列出LoadBalancer(简称LB∶负载均衡）后面所有的机器，Ribbon会自动的帮助  
你基于某种规则(如简单轮询，随机连接等等）去连接这些机器。我们也很容易使用Ribbon实现自定义的负载均衡算法  

### ribbon能干嘛?  
- LB，即负载均衡(Load Balance)，在微服务或分布式集群中经常用的一种应用。
- 负载均衡简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA (高可用)。
- 常见的负载均衡软件有Nginx，Lvs等等
- dubbo、SpringCloud中均给我们提供了负载均衡，SpringCloud的负载均衡算法可以自定义负载均衡简单分类:
  - 负载均衡简单分类：
    - 集中式LB
      - 即在服务的消费方和提供方之间使用独立的LB设施，如Nginx，由该设施负责把访问请求通过某种策略转发至服务的提供方!
    - 进程式LB
      - 将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选出一个合适的服务器。
      - Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址


## Ribbon使用
- 导入依赖  
由于客户端要向Eureka中获取注册列表，因此两个依赖都要  
```xml
<!-- 负载均衡Ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>

<!-- eureka中获取注册列表 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
- 编写配置文件
```properties
server:
  port: 80

# eureka配置
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/  # 哪些注册中心可以提供服务
```
- 为RestTemplate配置类添加注解
```java
@Configuration
public class ConfigBean {
    @Bean
    //配置负载均衡实现
    @LoadBalanced  //Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

## 自定义负载均衡策略

**官方解释**  
Spring Cloud还允许您通过使用@RibbonClient声明其他配置（位于RibbonClientConfiguration之上）来完全控制客户端。例：  
```java
@Configuration
@RibbonClient(name = "foo", configuration = FooConfiguration.class)
public class TestConfiguration {
}
```


- 注意不要和springboot主启动类同级，以免被扫描直接替换组件，在启动类的上一级目录创建一个文件夹  
编写一个配置类和一个自定义负载均衡策略
- 配置类
```java
@Configuration
public class EngulfRule {
    @Bean
    public IRule myRule(){
        return new EngulfRandomRule();  //使用自定义负载均衡策略
    }
}
```

- 自定义负载均衡策略(参考IRule接口的实现类)
```java
public class EngulfRandomRule extends AbstractLoadBalancerRule {

    //每个服务访问5次，换下一个服务(共3个服务)

    //total=0，默认=0，如果=5，我们指向下一个服务节点
    //index=0 如果total=5 index+1 total重置

    private int total = 0;  //被调用的次数
    private int currentIndex = 0;
    
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;

            while(server == null) {
                if (Thread.interrupted()) {
                    return null;
                }

                List<Server> upList = lb.getReachableServers();  //获得活着的服务
                List<Server> allList = lb.getAllServers();  //获得全部的服务
                int serverCount = allList.size();
                if (serverCount == 0) {
                    return null;
                }

//                int index = this.chooseRandomInt(serverCount);  //生成区间随机数
//                server = (Server)upList.get(index);  //从活着的服务中随机获取

                //====================================== 开始自定义规则

                if(total < 5){
                    server = upList.get(currentIndex);
                    total++;
                }else {
                    total = 0;
                    currentIndex++;
                    if (currentIndex > upList.size() - 1){
                        currentIndex = 0;
                    }
                    server = upList.get(currentIndex);  //从活着的服务中，获取指定的服务来进行操作
                }

                //======================================

                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }

                    server = null;
                    Thread.yield();
                }
            }

            return server;
        }
    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
```

- 在启动类中添加注解
```java
@SpringBootApplication
@EnableEurekaClient
//在微服务启动的时候就能去加载我们自定义负载均衡类(IRule)
@RibbonClient(name = "SPRINGCLOUD-PROVIDER-DEPT",configuration = EngulfRule.class)  //name对哪个服务进行负载均衡,指定配置类
public class DeptConsumer_80 {
    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer_80.class,args);
    }
}
```
