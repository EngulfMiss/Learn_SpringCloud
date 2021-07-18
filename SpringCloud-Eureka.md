# Eureka服务注册与发现
- 三大角色
  - Eureka Server：提供服务的注册与发现
  - Service Provider：将自身服务注册到Eureka中，从而使消费方能够找到
  - Service Consumer：服务消费方从Eureke中获取注册服务列表，从而找到消费服务

**使用**  
### Eureka Server使用
- 导入依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
- 配置Eureka Server
```yml
server:
  port: 7001

# Eureka 配置
eureka:
  instance:
    hostname: eurekalocalhost  # Eureka服务端的实例名称
  client:
    register-with-eureka: false # 表示是否向eureka注册中心注册自己
    fetch-registry: false # fetch-registry如果为false，则表示自己为注册中心
    service-url:  # 与服务注册中心交互的地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

- 配置类开启支持
```java
@SpringBootApplication
@EnableEurekaServer  // @EnableEurekaServer  服务端的启动类，可以接受别人注册进来
public class EurekaServer_7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer_7001.class,args);
    }
}
```

**Client与上面类似**

## CAP
- CAP是什么?
  - C(Consistency)：强一致性
  - A(Availability)：可用性
  - P(Partition tolerance)：分区容错性

## Eureka和Zookeeper对比
作为服务注册中心，Eureka比zookeeper好在哪里?   
著名的CAP理论指出，一个分布式系统不可能同时满足C(一致性)、A (可用性)、P(容错性)。由于分区容错性P在分布式系统中是必须要保证的，因此我们只能在A和C之间进行权衡。  
- Zookeeper保证的是CP;
- Eureka保证的是AP;

**Zookeeper保证的是CP**
当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用,也就是说，服务注册功能对一致性的要求要高于可用  
性。但是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30~120s，且选举  
期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因为网络问题使得zk集群失去master节点是较大概率会发生的事件，虽然服务最终能够  
恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的。  

**Eureka保证的是AP**
Eureka看明白了这一点，因此在设计时就优先保证可用性。Eureka各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而  
Eureka的客户端在向某个Eureka注册时，如果发现连接失败，则会自动切换至其他节点，只要有一台Eureka还在，就能保住注册服务的可用性，只不过查到的信息可能不是最新的  
除此之外，Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况:
1. Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务
2. Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其他节点上(即保证当前节点依然可用)
3. 当网络稳定时，当前实例新的注册信息会被同步到其他节点中  
**因此，Eureka可以很好的应对因网络故障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪**
