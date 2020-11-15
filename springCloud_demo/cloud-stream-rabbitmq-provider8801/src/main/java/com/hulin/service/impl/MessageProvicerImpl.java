package com.hulin.service.impl;

import com.hulin.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@EnableBinding(Source.class)//定义消息的推送管道 指信道channel和exchange绑定在一起
public class MessageProvicerImpl implements IMessageProvider {
    @Autowired
    private MessageChannel output;
    @Override
    public String send() {
        String serial= UUID.randomUUID().toString();
        //向交换机发送消息
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("***serial: "+serial);
        return null;
    }
}
