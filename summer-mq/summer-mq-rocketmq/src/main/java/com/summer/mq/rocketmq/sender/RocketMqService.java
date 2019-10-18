package com.summer.mq.rocketmq.sender;

import com.summer.common.mq.MessageSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;


@Component
@Slf4j
public class RocketMqService implements MessageSender {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    public void init() {

//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate


    }

    @Data
    @AllArgsConstructor
    public static class OrderPaidEvent implements Serializable {
        private String orderId;

        private BigDecimal paidMoney;
    }


    @Override
    public void sendMsg(Object object) {
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));


        // Build a SpringMessage for sending in transaction
        Message msg = MessageBuilder.withPayload("hello world!").build();
        // In sendMessageInTransaction(), the first parameter transaction name ("test")
        // must be same with the @RocketMQTransactionListener's member field 'transName'
        rocketMQTemplate.sendMessageInTransaction("test", "test-topic", msg, null);


    }


}
