package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hystrix服务降级更多在消费端中使用 当然也可以在提供端使用
 * 80端调用8001服务，一方面8001端作服务降级，另一方面80端也可以更好的保护自己，如果8001超时 那么80就服务降级
 * 第一步：修改yml配置文件
 * 第二步：主启动类加开启注解
 * 第三步：业务类
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class FeignHystrixOrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignHystrixOrderMain80.class,args);
    }
}
/**
 *  1.调用服务失败会触发服务降级，而服务降级会调用fallback方法
 *  2.但无论如何 降级的流程一定会先调用正常方法再调用fallback方法
 *  3.假如单位时间内调用失败次数过多，也就是服务降级次数过多，则触发熔断
 *  4.熔断以后就会跳过正常方法直接调用fallback方法
 *  5.所谓“熔断后服务不可用”就是因为跳过了正常方法直接执行fallback
 */
