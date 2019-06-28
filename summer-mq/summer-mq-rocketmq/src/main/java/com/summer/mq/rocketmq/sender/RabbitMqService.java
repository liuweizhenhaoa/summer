package com.summer.mq.rocketmq.sender;

import com.summer.common.mq.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitMqService implements MessageSender {


    @Override
    public void sendMsg(Object object){

    }




}
