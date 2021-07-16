## SpringCloud的父工程依赖
```xml
    <!-- 打包方式 -->
    <packaging>pom</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- springcloud依赖 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR9</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- springboot依赖 -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.3.11.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- 数据库 -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.48</version>
            </dependency>
            <!-- 数据源 -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.1.17</version>
            </dependency>

            <!-- SpringBoot 启动器 -->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>2.2.0</version>
            </dependency>

            <!-- junit -->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>

            <!-- log4j -->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>1.2.17</version>
            </dependency>

            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-core</artifactId>
                <version>1.2.3</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
```


## 环境搭建
- 创建一个数据库，写个表
```sql
create table dept
(
    deptId    int auto_increment
        primary key,
    deptName  varchar(40) null,
    db_source varchar(60) null
)
```
- 编写一个对应的实体类(记得要序列化)
```java
@Data
@NoArgsConstructor
@Builder
//使用分布式，实体类需要序列化
public class Dept implements Serializable {    // Dept 实体类 orm 对象关系映射
    private Integer deptId;
    private String deptName;

    //判断数据是存在哪个数据库的字段  微服务的特性  一个服务可以对应一个数据库，同一个信息也可能存在不同的数据库
    private String db_source;

    public Dept(String deptName) {
        this.deptName = deptName;
    }
}
```
