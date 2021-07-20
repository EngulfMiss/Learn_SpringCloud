## Zuul路由网关
- 什么是Zuul?
Zuul包含了对请求的路由和过滤两个最主要的功能：  
其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问同一入口的基础，而过滤器功能  
则负责对请求的处理过程进行干预，是实现请求校验，服务聚合等功能的基础。Zuul和Eureka进行整合，将   
Zuul自身注册为Eureka服务治理下的应用，同时从Eureka中获得其他微服务的消息，也即以后的访问微服务  
都是通过Zuul跳转后获得。  

注意：Zuul服务最终还是会注册进Eureka  
提供：代理 + 路由 + 过滤  三大功能


## Zuul使用
- 创建项目，导入依赖(核心Eureka+Zuul)
```xml
<dependencies>


<!-- Zuul -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
<!--            <version>2.2.9.RELEASE</version>-->
</dependency>



<!-- Hystrix监控 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>


<!-- Hystrix -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>

<!-- 负载均衡Ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>

<!-- erueka中获取注册列表 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

<dependency>
    <groupId>org.engulf</groupId>
    <artifactId>springcloud-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
</dependencies>
```
