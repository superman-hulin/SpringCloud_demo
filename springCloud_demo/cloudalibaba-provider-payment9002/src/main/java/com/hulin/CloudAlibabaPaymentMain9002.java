package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaPaymentMain9002 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaPaymentMain9002.class,args);
    }
}
