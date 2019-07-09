package com.summer.dt.mq.mq.rabbitmq.sender;

import com.summer.common.exception.BussinessException;
import com.summer.dt.mq.common.constant.OrderConstant;
import com.summer.dt.mq.service.TransactionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@Slf4j
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    TransactionLogService transactionLogService;

    @Autowired
    RabbitTemplate rabbitTemplate ;


    @PostConstruct
    public void init() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);// 指定 ConfirmCallback
        rabbitTemplate.setReturnCallback(this);// 指定 ReturnCallback
    }


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

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {

    }
}
