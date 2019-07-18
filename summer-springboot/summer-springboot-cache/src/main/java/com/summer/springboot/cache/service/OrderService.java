package com.summer.springboot.cache.service;


import com.summer.springboot.cache.entity.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(long id);


    Order queryOrder(long id);

    List<Order> queryAll(int pageNum,int pageSize);
}
