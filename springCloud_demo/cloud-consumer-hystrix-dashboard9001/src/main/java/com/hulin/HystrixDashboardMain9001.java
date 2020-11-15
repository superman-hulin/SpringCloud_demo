package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
/**
 * 使用HystrixDashboard监控微服务提供者
 *      localhost:9001/hystrix  进入Hystrix首页
 *      然后输入需要监控的微服务地址 则可进入监控
 * 现在使用9001监控8001服务
 *      修改8001服务：
 *          1.确保引入了spring-boot-starter-actuator依赖
 *          2.配置注入一个bean
 *          3.加@EnableCircuitBreaker
 *      输入监控8001服务的地址：http://localhost:8001/hystrix.stream
 */
public class HystrixDashboardMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMain9001.class,args);
    }
}
