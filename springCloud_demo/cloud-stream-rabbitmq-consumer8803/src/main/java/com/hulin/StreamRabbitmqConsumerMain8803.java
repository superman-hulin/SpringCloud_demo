package com.hulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**问题：
 *  1. 当8801发送消息时，消费者8802和8803都同时收到消息了 产生了重复消费问题
 *  2. 消息持久化问题
 * 解决
 *   1.重复消费问题
 *    使用分组的特性来解决
 *    在stream中处于同一个group中的多个消费者是竞争关系，就能保证消息只会被其中一个应用消费一次
 *    不同组是可以全面消费的（重复消费） 也就是说8802和8803是不同组
 *    8802和8803启动时会默认分别创建一个队列，都绑定到8801的交换机了，则都会收到8801的消息
 *    则我们自定义分组，即在8002和8003的yml文件中配置group属性，该值会作为该服务的队列名
 *    则使它们的组名一样
 *
 *
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamRabbitmqConsumerMain8803 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitmqConsumerMain8803.class,args);
    }
}
