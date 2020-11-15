package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OpenFeign使用
 *      接口+注解：微服务调用接口+@FeignClient
 * OpenFeign超时控制：
 *      消费方调用提供方服务时，有些业务操作比较耗时，可能会等待
 *      OpenFeign中 消费者默认等待1秒钟 超过后会报错
 *      则需要设置超时控制
 */
@SpringBootApplication
@EnableFeignClients //启动feign
public class FeignOrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignOrderMain80.class,args);
    }
}
