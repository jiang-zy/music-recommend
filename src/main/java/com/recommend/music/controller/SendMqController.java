package com.recommend.music.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMqController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/hello")
    public String hello(){
        rabbitTemplate.convertAndSend("balance","hello java mq");
        return "消息发送成功";
    }

}
