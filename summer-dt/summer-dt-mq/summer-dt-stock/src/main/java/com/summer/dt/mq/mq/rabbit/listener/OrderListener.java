package com.summer.dt.mq.mq.rabbit.listener;

import com.rabbitmq.client.Channel;
import com.summer.common.exception.BussinessException;
import com.summer.dt.mq.model.order.Order;
import com.summer.dt.mq.service.StockService;
import lombok.extern.slf4j.Slf4j;


import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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


    // Binding listening, which can be generated automatically on the platform without configuration
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(value = RABBITMQ_EXCHANGE_ORDER, durable = "true", type = "topic", ignoreDeclarationExceptions = "true"), key = "order.stock"))
    // Manually signed must be dependy channel
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               @Headers Map<String, Object> headers,
                               Channel channel) {
        log.info("order ID:" + order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        stockService.reduceStock(1, 1);


        // ACK-Manually signed
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            throw new BussinessException("", -1);
        }
    }
}
