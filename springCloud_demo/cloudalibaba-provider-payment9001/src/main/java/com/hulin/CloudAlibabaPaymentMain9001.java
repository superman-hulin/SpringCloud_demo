package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaPaymentMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaPaymentMain9001.class,args);
    }
}
