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
    RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void init() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);//  ConfirmCallback
        rabbitTemplate.setReturnCallback(this);//  ReturnCallback
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        //if send fialed
        if (!b) {
            log.info("send message failed: " + s);
            throw new BussinessException("send error " + s);
        }
        //send success and update status of the table  Transaction
        String[] arrays = correlationData.getId().split("_");//primaryKey_type
        if (arrays.length > 0) {
            transactionLogService.updateTransactionStatus(Long.valueOf(arrays[0]), arrays[1],
                    OrderConstant.TRANSACTION_SUCCESS, new Date());
        }

    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("returnedMessage: " + s);

    }
}
