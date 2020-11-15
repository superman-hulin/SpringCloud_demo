package com.hulin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class OrderConsulController {
    /**
     * 调用其它服务 最原始使用httpClient
     * 目前使用restTemplate
     *     提供了多种便捷访问远程http服务的方法
     *     是一种简单便捷的访问restful服务模板类 是spring提供的用于访问Rest服务的客户端模板工具集
     */
    private static final String PAYMENT_URL="http://consul-payment";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public String paymentInfo(){
        String result=restTemplate.getForObject(PAYMENT_URL+"/payment/consul",String.class);
        return result;
    }
}
