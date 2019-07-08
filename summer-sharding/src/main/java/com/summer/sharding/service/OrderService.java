package com.summer.sharding.service;


import com.summer.sharding.entity.Order;
import com.summer.sharding.entity.OrderInfo;
import com.summer.sharding.entity.OrderRequest;

import java.util.List;

public interface OrderService {

    List<Order> getOrder(OrderRequest orderRequest);

    List<Order> getOrderById(OrderRequest orderRequest);

    List<Order> getOrders();

    void addOrder(Order order);

    List<OrderInfo> getOrderInfo(OrderRequest orderRequest);
}
