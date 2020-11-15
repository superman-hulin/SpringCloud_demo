package com.hulin.controller;

import com.hulin.service.PaymentHystrixFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class HystrixFeignOrderController {
    @Autowired
    private PaymentHystrixFeignService paymentHystrixFeignService;
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    @HystrixCommand//用全局兜底 如果不加该注解 表示该方法不需要服务降级
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result=paymentHystrixFeignService.paymentInfo_OK(id);
        log.info("***result： "+result);
        return result;
    }
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    /**
     * 80端也定义 调用8001端超时后 走paymentInfo_TimeoutHandler兜底方法
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            //定义等待8001端的超时时间
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        int x=10/0;
        String result=paymentHystrixFeignService.paymentInfo_Timeout(id);
        log.info("***result： "+result);
        return result;
    }
    public String paymentInfo_TimeoutHandler(Integer id){
        return "我是消费者80端 paymentInfo_TimeoutHandler";
    }

    /**
     * 每个需要服务降级的方法都像上述这样写的话，会有如下问题
     *   1. 每个业务方法后面都跟着一个服务降级方法 耦合度过高
     *   2. 每个方法都需要写一个兜底方法和前面的@HystrixCommand注解配置 导致代码膨胀
     *  解决方法：
     *      1. 解决代码膨胀
     *      统一的兜底方法和自定义分开，也就是说大部分业务方法都可沿用统一的 只有特定的需要自定义
     *      则定义一个全局通用的服务降级方法
     *         在类上加注解@DefaultProperties
     *         该类中方法的@HystrixCommand如果没有配置具体的兜底方法时，则走@DefaultProperties中的兜底方法
     *         如果方法需要自定义兜底方法 不走全局的 则在自己的@HystrixCommand注解中配兜底方法
     *       2. 解决耦合
     *       新建业务接口PaymentHystrixFeignService的实现类 该实现类中的方法就是对应接口中每个方法的兜底方法
     *       这样的话 在controller中不用为每个方法后面都跟着一个兜底方法 并且也无需加@HystrixCommand注解
     */
    //全局兜底方法
    public String payment_Global_FallbackMethod(){
        return "全局兜底方法";
    }
}
