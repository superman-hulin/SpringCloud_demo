package com.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon：主要功能是提供客户端的软件负载均衡算法和服务调用
 *         属于进程内LB 消费方服务使用该负载均衡去注册中心访问集群版的提供服务
 *         Nginx是服务端的LB 属于集中式LB 相当于整个网站的大门
 *  使用： 负载均衡+RestTemplate调用
 *  Ribbon中负载均衡算法： 提供了顶层接口IRule以及相应的多个实现（轮询、随机、重试等等）
 *  如何替换负载均衡算法
 *      增加自定义配置类 注意该类不能放在@ComponentScan所能扫描的包下
 *      即该类所在的包不能和主启动类在同包下，因为主启动类会扫描该类所在包及其子包
 *
 *      然后在主启动类添加@RibbonClient
 *  Ribbon在工作时分为两步
 *      1.先选择EurekaServer 它优先选择在同一个区域内负载较少的server
 *      2.再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址
 *
 *
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){
        //定义为随机
        return new RandomRule();
    }
}
