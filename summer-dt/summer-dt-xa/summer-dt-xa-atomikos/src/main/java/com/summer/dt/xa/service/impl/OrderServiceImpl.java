package com.summer.dt.xa.service.impl;

import com.summer.common.idgenerate.SnowflakeIdWorker;
import com.summer.dt.xa.dao.order.OrderMapper;
import com.summer.dt.xa.entity.Order;
import com.summer.dt.xa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;


    @Transactional
    @Override
    public Order saveOrder(Order order) {
        order.setId(snowflakeIdWorker.nextId());
        orderMapper.save(order);

        return order;
    }
}
