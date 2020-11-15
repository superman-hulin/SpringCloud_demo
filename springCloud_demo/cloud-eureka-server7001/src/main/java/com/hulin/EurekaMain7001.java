package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册：将服务信息注册进注册中心
 * 服务发现：从注册中心上获取服务信息
 * 实质：存key服务名 取value调用地址
 *
 * 工作流程：
 *  1.先启动eureka注册中心
 *  2.启动服务提供者payment支付服务
 *  3.支付服务启动后会把自身信息注册进Eureka
 *  4.消费者order服务在需要调用接口时，使用服务别名去注册中心获取实际的RPC远程调用地址
 *  5.消费者获得调用地址后，底层实际是利用HttpClient技术实现远程调用
 *  6.消费者获得服务地址后会缓存在本地jvm内存中，默认每间隔30秒更新一次服务调用地址
 *
 *  微服务RPC远程服务调用最核心的是什么
 *      高可用 如果注册中心单机，那么出故障的话 会导致整个服务环境不可用
 *      解决方法：搭建Eureka注册中心集群，实现负载均衡+故障容错
 *  集群注册的原理
 *      互相注册 相互守望
 */
@SpringBootApplication
@EnableEurekaServer //指明该服务是Eureka的服务注册中心
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}
