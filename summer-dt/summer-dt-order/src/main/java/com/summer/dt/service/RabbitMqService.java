package com.summer.dt.service;

import com.summer.dt.common.constant.OrderConstant;
import com.summer.dt.common.exception.BussinessException;
import com.summer.dt.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@Slf4j
public class RabbitMqService {

    @Autowired
    RabbitTemplate rabbitTemplate ;

    @Autowired
    TransactionLogService transactionLogService;

    private static final String RABBITMQ_EXCHANGE_ORDER = "order-exchange";


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
                String[] arrays = correlationData.getId().split("_");//primaryKey_type
                transactionLogService.updateTransactionStatus(Long.valueOf(arrays[0]), arrays[1],
                        OrderConstant.TRANSACTION_SUCCESS, new Date());
            }
        });
    }

    public void sendMsg(Order order){

        rabbitTemplate.convertAndSend(RABBITMQ_EXCHANGE_ORDER,"order.stock",order,
                new CorrelationData(String.valueOf(order.getId())+"_"+OrderConstant.TRANSACTION_TYPE_ORDER));
    }

}
