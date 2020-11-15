package com.hulin.service;

import com.hulin.entities.CommonResult;
import com.hulin.entities.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "PAYMENT-SERVICE") //值是需要调用的服务的名称
//该接口中方法对应需要调的服务中业务层接口方法
public interface PaymentFeignService {

    /**
     * 直接复制调用服务controller层相对应的方法 但是无需方法体
     * @param id
     * @return
     */
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
}
