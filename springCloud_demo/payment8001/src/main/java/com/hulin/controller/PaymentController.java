package com.hulin.controller;

import com.hulin.entities.CommonResult;
import com.hulin.entities.Payment;
import com.hulin.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    //服务发现
    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
       int result= paymentService.create(payment);
       log.info("****插入结果："+result);
       if(result>0){
           return new CommonResult(200,"插入成功,serverPort:"+serverPort,result);
       }else {
           return new CommonResult(444,"插入失败");
       }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment= paymentService.getPaymentById(id);
        log.info("****插入结果："+payment);
        if(payment!=null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        }else {
            return new CommonResult(444,"查询失败");
        }
    }

    /**
     * 对于注册进eureka里面的微服务，可以通过服务发现来获得该服务的信息
     * 比如主机名、端口号等 相当于调用服务者希望在注册中心中看到已注册服务的一些信息
     * 该功能的实现 需要在提供服务中实现
     *      1. 编写discovery方法
     *      2. 在主启动类中增加注解@EnableDiscoveryClient
     */
    @GetMapping("/payment/discovery")
    public Object discovery(){
        //列出在eureka注册中心中注册好的服务有哪些
       List<String> services= discoveryClient.getServices();
       for(String element:services){
           log.info("***element:"+element);
       }

       //根据在注册中心的服务名获取该服务的信息 之所以是集合 是因为该服务名下可能对应多个服务
       List<ServiceInstance> instances= discoveryClient.getInstances("PAYMENT-SERVICE");
       for(ServiceInstance instance:instances){
           log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
       }
       return this.discoveryClient;
    }
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    /**
     * Spring cloud Sleuth分布式请求链路追踪
     *      为什么出现该技术
     *          在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的服务节点调用来协同产生最后的请求结果，
     *        每一个前端请求都会形成一条复杂的分布式服务调用链路，链路中任何一环出现高延时或错误都会引起整个请求最后的失败。
     *      Spring cloud Sleuth可以进行服务跟踪，并兼容支持Zipkin(对跟踪进行展示)
     *      zipkin
     *          springcloud F版后无需构建Zipkin Server了 只需要下载jar包运行
     *          运行后 输入http://localhost:9411/zipkin/访问
     *
     */
    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "paymentZipkin server";
    }

}
