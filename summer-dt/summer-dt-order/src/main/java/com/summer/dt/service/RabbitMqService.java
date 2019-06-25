package com.summer.dt.service;

import com.summer.dt.common.exception.BussinessException;
import com.summer.dt.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RabbitMqService {

    @Autowired
    RabbitTemplate rabbitTemplate ;

    @Autowired
    TransactionLogService transactionLogService;

    @PostConstruct
    public void setUp(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {

                //如果没有发送成功
                if (!b) {
                    log.info("send message failed: " + s);
                    throw new BussinessException("send error " + s);
                }
                //发送成功，则更新事务表的状态为已发送
                transactionLogService.updateTransactionStatus(Long.valueOf(correlationData.getId()));
            }
        });
    }


    public void sendMsg(Order order){


        rabbitTemplate.convertAndSend("","","",
                new CorrelationData(String.valueOf(order.getId())));
    }

}
