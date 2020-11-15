package com.hulin.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;
    @RequestMapping("/payment/zk")
   public String paymentZookeeper(){
       return "springCloud with zookeeper:"+serverPort+"\t"+ UUID.randomUUID().toString();
   }

}
