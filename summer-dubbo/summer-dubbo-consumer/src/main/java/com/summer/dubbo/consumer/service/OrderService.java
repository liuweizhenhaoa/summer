package com.summer.dubbo.consumer.service;


import com.summer.dubbo.consumer.order.Order;

public interface OrderService {
    Order saveOrder(Order order);
}
