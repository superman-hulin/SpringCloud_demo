package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
/**
 * 网关也是一种服务，需要注册到注册中心
 * 网关在整个架构的位置
 *      各种终端--》nginx--》网关--》各种微服务
 * 在8001服务之前套一层网关
 *      即不想暴露服务8001的地址，只暴露网关的地址，则需要在网关中配置路由和断言，才能转发请求到8001服务
 *      即在配置文件中配置相关的路由和断言
 *      也可以通过编码的方式进行配置
 *  网关的结构
 *      路由  断言 过滤器
 *  过滤器
 *     指的是spring中GatewayFilter的实例，使用过滤器可以在请求被路由前或者之后对请求进行修改
 *     有两种：
 *          提供30多个单一过滤器
 *          提供10个全局过滤器
 *     可自定义全局过滤器
 */
public class GatewayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain9527.class,args);
    }
}
