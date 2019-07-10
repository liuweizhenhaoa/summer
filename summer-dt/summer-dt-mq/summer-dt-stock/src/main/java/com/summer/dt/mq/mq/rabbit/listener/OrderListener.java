package com.summer.dt.mq.mq.rabbit.listener;

import com.rabbitmq.client.Channel;
import com.summer.common.exception.BussinessException;
import com.summer.dt.mq.model.order.Order;
import com.summer.dt.mq.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class OrderListener {

    private static final String RABBITMQ_EXCHANGE_ORDER = "order-exchange";

    @Autowired
    StockService stockService;


    // 绑定监听，可以在未配置的情况下，在平台自动生成
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(value = RABBITMQ_EXCHANGE_ORDER, durable = "true", type = "topic", ignoreDeclarationExceptions = "true"), key = "order.stock"))
    // 手动签收必须依赖channel
    @RabbitHandler // 标识该方法，如果有消息过来，消费者调用该方法
    public void onOrderMessage(@Payload Order order,
                               @Headers Map<String, Object> headers,
                               Channel channel) {
        // 消费者操作
        log.info("订单ID：" + order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        stockService.reduceStock(1,1);


        // ACK-手工签收
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            throw new BussinessException("",-1);
        }
    }
}