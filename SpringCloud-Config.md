## SpringCloud config 分布式配置
### 概述
**分布式系统面临的--配置文件的问题**  
微服务意味着要将单体应用中的业务拆分成一个个子服务，每个服务的粒度相对较小，因此系统中会出现大量的服务，  
由于每个服务都需要必要的配置信息才能运行，所以一套集中式的，动态的配置管理设施是必不可少的。SpringCloud  
提供了ConfigServer来解决这个问题，我们每一个微服务自己带着一个application.yml，那上百的的配置文件要修  
改起来，岂不是要发疯!

**什么是SpringCloud config分布式配置中心**  
Spring Cloud Config 为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所  
有环节提供了一个中心化的外部配置  
Spring Cloud Config 分为服务端和客户端两部分;  
服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息，加密  
，解密信息等访问接口。  
客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载  
配置信息。配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理。并且可以通过git客户端  
工具来方便的管理和访问配置内容。