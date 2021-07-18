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

