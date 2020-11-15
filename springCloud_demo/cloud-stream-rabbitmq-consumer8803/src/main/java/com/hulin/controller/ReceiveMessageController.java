package com.hulin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Sink.class)
public class ReceiveMessageController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT) //监听队列 用于消费者的队列的消息接收
    public void input(Message<String> message){
        System.out.println("消费者2号,---->接收到的消息："+message.getPayload()+"\t port: "+serverPort);

    }

}
