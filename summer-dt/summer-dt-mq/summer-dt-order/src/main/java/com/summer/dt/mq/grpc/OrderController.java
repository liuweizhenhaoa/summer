package com.summer.dt.mq.grpc;

import com.summer.dt.mq.entity.Order;
import com.summer.dt.mq.mq.rabbitmq.sender.RabbitMqService;
import com.summer.dt.mq.service.OrderService;
import com.summer.dt.proto.OrderGrpc;
import com.summer.dt.proto.OrderOuterClass;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * GreeterService
 */
@GRpcService
public class OrderController extends OrderGrpc.OrderImplBase {

    @Autowired
    OrderService orderService;

    @Autowired
    RabbitMqService rabbitMqService;

    @Override
    public void saveOrder(OrderOuterClass.OrderSaveRequest request, StreamObserver<OrderOuterClass.OrderSaveReply> respoObserver) {
        final OrderOuterClass.OrderSaveReply.Builder replyBuilder = OrderOuterClass.OrderSaveReply.newBuilder();
        Order order = new Order();
        order.setPrice(request.getPrice());
        order.setDetail(request.getDetail());
        order.setCreateTime(new Date());

        //1 save order and insert transaction log
        orderService.saveOrder(order);
        //2 send MQ
        rabbitMqService.sendMsg(order);

        replyBuilder.setCreateTime(order.getCreateTime().getTime())
                .setDetail(order.getDetail())
                .setId(order.getId())
                .setPrice(order.getPrice());

        respoObserver.onNext(replyBuilder.build());
        respoObserver.onCompleted();
    }

}