package com.hulin.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 将restTemplate对象注入spring容器
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    /**
     * @LoadBalanced
     *      当该服务需要请求的服务是集群版时，那么请求服务的地址就需要写成该服务集群对外暴露的统一名称
     *      但是RestTemplate在对该地址发请求时，无法确定该集群中哪台进行服务
     *      则使用该注解赋予RestTemplate负载均衡的能力
     */
    //@LoadBalanced 注掉 自己写轮询算法
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
