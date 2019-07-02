package com.summer.sharding.service.impl;

import com.summer.sharding.common.util.IdGenerateUtil;
import com.summer.sharding.dao.OrderItemMapper;
import com.summer.sharding.dao.OrderMapper;
import com.summer.sharding.entity.Order;
import com.summer.sharding.entity.OrderInfo;
import com.summer.sharding.entity.OrderItem;
import com.summer.sharding.entity.OrderRequest;
import com.summer.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public List<Order> getOrder(OrderRequest orderRequest) {
        return orderMapper.getOrder(orderRequest);
    }

    @Override
    public List<Order> getOrderById(OrderRequest orderRequest) {
        return orderMapper.getOrderById(orderRequest);
    }

    @Override
    public List<Order> getOrders() {
        return orderMapper.getOrders();
    }

    @Override
    public void addOrder(Order order) {
        addSnowId(order, null);

        orderMapper.addOrder(order);

    }

    @Override
    public List<OrderInfo> getOrderInfo(OrderRequest orderRequest) {
        return orderMapper.getOrderInfo(orderRequest);
    }

    /**
     * 给订单和订单项设置雪花id
     *
     * @param order
     * @param orderItems
     * @author cluo
     * @date 2018/08/03
     */
    private void addSnowId(Order order, List<OrderItem> orderItems) {
        Long snowId = IdGenerateUtil.getSnowId();
        order.setOrderId(snowId + new Random().nextInt(2));

    }
}
