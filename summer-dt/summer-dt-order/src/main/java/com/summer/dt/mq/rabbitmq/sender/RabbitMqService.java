package com.summer.dt.mq.rabbitmq.sender;

import com.summer.dt.common.constant.OrderConstant;
import com.summer.dt.common.exception.BussinessException;
import com.summer.dt.entity.Order;
import com.summer.dt.mq.MessageSender;
import com.summer.dt.service.TransactionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@Slf4j
public class RabbitMqService implements MessageSender {

    @Autowired
    RabbitTemplate rabbitTemplate ;

    @Autowired
    TransactionLogService transactionLogService;

    private static final String RABBITMQ_EXCHANGE_ORDER = "order-exchange";


//    @PostConstruct
//    public void setUp(){
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean b, String s) {
//
//                log.info("-------------------------------------");
//
//                //如果没有发送成功
//                if (!b) {
//                    log.info("send message failed: " + s);
//                    throw new BussinessException("send error " + s);
//                }
//                //发送成功，则更新事务表的状态为已发送
//                String[] arrays = correlationData.getId().split("_");//primaryKey_type
//                transactionLogService.updateTransactionStatus(Long.valueOf(arrays[0]), arrays[1],
//                        OrderConstant.TRANSACTION_SUCCESS, new Date());
//            }
//        });
//    }

    @Override
    public void sendMsg(Object object){
        rabbitTemplate.convertAndSend(RABBITMQ_EXCHANGE_ORDER,"order.stock",object.toString(),
                objectToCorrelationData(object));
    }


    public CorrelationData objectToCorrelationData(Object object){
        CorrelationData correlationData = new CorrelationData();

        if(object instanceof Order){
            Order order = (Order) object;
            correlationData.setId(String.valueOf(order.getId())+"_"+OrderConstant.TRANSACTION_TYPE_ORDER);
        }else{
            log.error("-----------------消息类型未定义--------------------");
        }

        return correlationData;

    }

}
