package com.hulin.controller;

import com.hulin.entities.CommonResult;
import com.hulin.entities.Payment;
import com.hulin.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class OrderController {
    /**
     * 调用其它服务 最原始使用httpClient
     * 目前使用restTemplate
     *     提供了多种便捷访问远程http服务的方法
     *     是一种简单便捷的访问restful服务模板类 是spring提供的用于访问Rest服务的客户端模板工具集
     */
    //当需要请求的服务为单机时 则地址可以写成具体ip+端口
    //private static final String PAYMENT_URL="http://localhost:8001";
    //当需要请求的服务为集群时，则地址写服务名称
    private static final String PAYMENT_URL="http://PAYMENT-SERVICE";
    @Autowired
    //引用自己写的轮询负载均衡算法
    private LoadBalancer loadBalancer;
    //服务发现
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id){
         return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
    //测试自己写的轮询算法
    @GetMapping("consumer/payment/lb")
    private String getPaymentLB(){
        List<ServiceInstance> instances= discoveryClient.getInstances("PAYMENT-SERVICE");
        if(instances==null||instances.size()<=0){
            return null;
        }
        ServiceInstance serviceInstance=loadBalancer.instances(instances);
        URI uri=serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);

    }

    /**
     * Spring cloud Sleuth+ Zipkin
     * @return
     */
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        String result=restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin",String.class);
        return result;
    }
}
