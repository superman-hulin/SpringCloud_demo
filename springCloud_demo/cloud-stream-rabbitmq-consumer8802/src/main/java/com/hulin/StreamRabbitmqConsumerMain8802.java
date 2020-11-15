package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StreamRabbitmqConsumerMain8802 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitmqConsumerMain8802.class,args);
    }
}
