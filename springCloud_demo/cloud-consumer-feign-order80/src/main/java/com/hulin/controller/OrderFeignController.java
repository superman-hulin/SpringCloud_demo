package com.hulin.controller;

import com.hulin.entities.CommonResult;
import com.hulin.entities.Payment;
import com.hulin.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment>  getPayment(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }
}
