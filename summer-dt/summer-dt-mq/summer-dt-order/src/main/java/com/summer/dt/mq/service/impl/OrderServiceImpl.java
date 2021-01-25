package com.summer.dt.mq.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.summer.common.idgenerate.SnowflakeIdWorker;
import com.summer.common.utils.GsonUtils;
import com.summer.dt.mq.common.constant.OrderConstant;
import com.summer.dt.mq.dao.OrderMapper;
import com.summer.dt.mq.dao.TransactionLogMapper;
import com.summer.dt.mq.entity.Order;
import com.summer.dt.mq.entity.TransactionLog;

import com.summer.dt.mq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

//    @Autowired
//    OrderMapper orderMapper;

    @Autowired
    TransactionLogMapper transactionLogMapper;

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;


    @Transactional
    @Override
    public Order saveOrder(Order order) {
        order.setId(snowflakeIdWorker.nextId());
        baseMapper.save(order);

        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setCreateTime(new Date());
        transactionLog.setMsgBody(GsonUtils.toJson(order));
        transactionLog.setPrimaryKey(order.getId());
        transactionLog.setType(OrderConstant.TRANSACTION_TYPE_ORDER); //1:order
        transactionLog.setStatus(OrderConstant.TRANSACTION_INIT);//init

        transactionLogMapper.save(transactionLog);

        return order;
    }
}
