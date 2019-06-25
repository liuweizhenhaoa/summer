package com.summer.dt.grpc;

import com.summer.dt.entity.Order;
import com.summer.dt.proto.OrderGrpc;
import com.summer.dt.proto.OrderOuterClass;
import com.summer.dt.service.OrderService;
import com.summer.dt.mq.rabbitmq.sender.RabbitMqService;
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

        //1、保存订单并插入事务日志表
        orderService.saveOrder(order);
        //2、发送MQ
        rabbitMqService.sendMsg(order);

        replyBuilder.setCreateTime(order.getCreateTime().getTime())
        .setDetail(order.getDetail())
        .setId(order.getId())
        .setPrice(order.getPrice());

        respoObserver.onNext(replyBuilder.build());
        respoObserver.onCompleted();
    }

}