package com.summer.springboot.hbase.service;



import com.summer.springboot.hbase.entity.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(long id);


    Order queryOrder(long id);

    List<Order> queryAll(int pageNum,int pageSize);
}
