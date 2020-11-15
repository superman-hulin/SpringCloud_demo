package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Spring Cloud Config
 *    分布式系统面临的问题
 *      1.系统中模块相当多 每个模块都需要有一份application.yml配置文件 会导致代码膨胀
 *      2.配置文件多了 就需要有统一的管理 就比如有四个服务都连接同一个数据库 如果没有分布式统一配置中心的话 需要修改多处
 *      3.上线之后，有生产环境、测试环境、、、 那就需要有多套配置
 *   为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置(即github)
 *   分为服务端和客户端两部分
 *      服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息
 *      客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息
 *   相当于多个微服务都连接一个Config Server（配置中心）,然后Config Server通过本地git仓库连接github仓库
 *   流程：
 *      1.在github上新建仓库存放配置文件
 *      2.构建配置中心微服务模块 并做相关配置（连接github）
 *      3.启动配置中心微服务，从github上获取配置内容（http://cinfig3344.com:3344/master/config-dev.yml） 哪个分支下面的哪个文件名
 */
@SpringBootApplication
@EnableConfigServer //开启配置中心
@EnableEurekaClient
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class,args);
    }
}
