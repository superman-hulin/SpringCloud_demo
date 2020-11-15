package com.hulin.service.impl;


import cn.hutool.core.util.IdUtil;
import com.hulin.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池："+Thread.currentThread().getName()+"  paymentInfo_OK,id: "+id;
    }

    @Override
    //该注解表明该方法要是超时的话 使用fallbackMethod进行兜底 服务降级
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            //该注解是规定该线程的超时时间是3秒 即该线程的峰值是3秒
            //而该方法实际运行需要5秒 则会选择兜底方法
          @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_Timeout(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int age=10/0; //出现异常时 也会走兜底方法
        return "线程池："+Thread.currentThread().getName()+"  paymentInfo_Timeout,id: "+id;
    }
    /**
     * 当前服务不可用了 做服务降级 定义兜底方法
     */
    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"  8001paymentInfo_TimeoutHandler,id: "+id;
    }

    /**
     * 服务熔断
     * 1.调用服务失败会触发服务降级，而服务降级会调用fallback方法
     * 2.但无论如何 降级的流程一定会先调用正常方法再调用fallback方法
     * 3.假如单位时间内调用失败次数过多，也就是服务降级次数过多，则触发熔断
     * 4.熔断以后就会跳过正常方法直接调用fallback方法
     * 5.所谓“熔断后服务不可用”就是因为跳过了正常方法直接执行fallback
     * 服务降级---》服务熔断---》恢复链路
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //是否开启断路器
            @HystrixProperty(name="circuitBreaker.enabled",value="true"),
            //请求次数
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
            //时间窗口期
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
            //失败率达到多少后跳闸
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60"),
            //参数的意思：假设在这个时间窗口期内 这么多次请求里面有这么多失败率，那么断路器就会起作用
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("***id不能负数");
        }
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数 请稍后再试 id: "+id;
    }
}
