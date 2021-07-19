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
