package com.hulin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
/**
 * fallback参数是将兜底方法所在类配置过来
 * 当controller层中方法没有配@HystrixCommand时 兜底方法就用PaymentFallbackService中的
 * 如果controller中配了 则使用controller中的
 * 注意：当controller层中方法请求的服务宕机了，则消费端需要服务降级 这种宕机情况的服务降级方式可使用PaymentFallbackService
 *      但是如果是controller层中方法是出现异常了 则PaymentFallbackService这种方式处理不了这种异常降级
 *      还是需要在controller中该方法上使用配@HystrixCommand的方式
 */
@FeignClient(value = "HYSTRIX-PAYMENT-SERVICE",fallback = PaymentFallbackService.class)
public interface PaymentHystrixFeignService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id);
}
