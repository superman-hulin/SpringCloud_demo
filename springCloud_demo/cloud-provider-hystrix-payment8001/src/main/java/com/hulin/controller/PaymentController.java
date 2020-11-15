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

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result=paymentService.paymentInfo_OK(id);
        log.info("***result： "+result);
        return result;
    }
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        String result=paymentService.paymentInfo_Timeout(id);
        log.info("***result： "+result);
        return result;
    }

    /**
     *   测试服务熔断
     *      如果正常请求次数 id为正数时 则正常返回结果 为负数时 则正常调用fallback
     *      但是在固定的时间窗口期内 请求固定次数 然后达到固定的失败率 则断路器打开
     *      此时即使id为整数 也会走fallback
     *      但是过了一会 断路器会从打开状态转换为关闭状态 重新恢复正常的调用链路 则id为整数时 是正常返回
     *  断路器开启或者关闭的条件
     *      当满足一定的阈值的时候（默认10秒内超过20个请求）
     *      当失败率达到一定的时候（默认10秒内超过50%）
     *      到达以上阈值 断路器将会开启
     *      当开启的时候 所有请求都不会进行转发
     *      一段时间之后（默认是5秒） 这个时候断路器是半开状态 会让其中一个请求进行转发，如果成功，断路器会关闭，
     *      若失败，继续开启。重复4和5
     *  断路器打开之后：
     *      1.再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback。通过断路器，实现了自动的发现错误并将降级逻辑切换为主逻辑，
     *       减少响应延迟的效果
     *      2.原来的主逻辑要如何恢复
     *         hystrix为我们实现了自动恢复功能，当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间窗内，
     *         降级逻辑是临时的成为主逻辑，当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回
     *         那么断路器将继续闭合，主逻辑恢复，如果这次请求依然有问题，断路器继续进入打开状态 休眠时间窗重新计时。
     */
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result=paymentService.paymentCircuitBreaker(id);
        log.info("***result: "+result);
        return result;
    }

}
