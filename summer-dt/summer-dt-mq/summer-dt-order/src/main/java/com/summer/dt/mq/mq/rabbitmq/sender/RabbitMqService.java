package com.summer.dt.mq.mq.rabbitmq.sender;

import com.summer.common.mq.MessageSender;
import com.summer.dt.mq.common.constant.OrderConstant;
import com.summer.dt.mq.entity.Order;
import com.summer.dt.xa.service.TransactionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitMqService implements MessageSender {

    @Autowired
    RabbitTemplate rabbitTemplate ;

    @Autowired
    TransactionLogService transactionLogService;

    private static final String RABBITMQ_EXCHANGE_ORDER = "order-exchange";


    @Override
    public void sendMsg(Object object){
        rabbitTemplate.convertAndSend(RABBITMQ_EXCHANGE_ORDER,"order.stock",object.toString(),
                objectToCorrelationData(object));
    }


    public CorrelationData objectToCorrelationData(Object object){
        CorrelationData correlationData = new CorrelationData();

        if(object instanceof Order){
            Order order = (Order) object;
            correlationData.setId(String.valueOf(order.getId())+"_"+ OrderConstant.TRANSACTION_TYPE_ORDER);
        }else{
            log.error("-----------------消息类型未定义--------------------");
        }

        return correlationData;

    }

}
