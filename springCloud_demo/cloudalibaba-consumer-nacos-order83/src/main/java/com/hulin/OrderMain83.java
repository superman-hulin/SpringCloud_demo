package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Nacos
 *  一个更易于构建云原生应用的动态服务发现，配置管理和服务管理中心，即注册中心+配置中心的组合 Nacos=Eureka+Config+Bus
 * 安装
 *    下载压缩包解压 运行startup.cmd 启动访问http://localhost:8848/nacos
 *    
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderMain83 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain83.class,args);
    }

}
