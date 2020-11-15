package com.hulin.service;

import com.hulin.entities.Payment;
import org.springframework.web.bind.annotation.PathVariable;


public interface PaymentService {
   public String paymentInfo_OK(Integer id);
    public String paymentInfo_Timeout(Integer id);
    public String paymentCircuitBreaker(@PathVariable("id") Integer id);
}
