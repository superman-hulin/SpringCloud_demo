package com.hulin;

import com.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 订单服务类 面向终端用户访问 则该服务无须具体业务层和dao层
 */
@SpringBootApplication
@EnableEurekaClient
//当需要对Ribbon默认的LB算法（轮询）替换时 需要该注解 name是指定该服务要去访问的服务
@RibbonClient(name = "PAYMENT-SERVICE",configuration = MySelfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}
