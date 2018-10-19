package com.recommend.music.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


@Component
@RabbitListener(queues = "balance")
@Slf4j
public class Customer {

    @RabbitHandler
    public void process(byte[] body) throws UnsupportedEncodingException {
        String msg = new String(body, "UTF-8");
        log.info("接收到的消息：message:{}",msg);
    }

}
