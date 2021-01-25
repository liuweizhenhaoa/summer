package com.summer.dubbo.provider.service.impl;

import com.summer.common.idgenerate.SnowflakeIdWorker;
import com.summer.common.utils.GsonUtils;
import com.summer.dubbo.provider.constant.OrderConstant;
import com.summer.dubbo.provider.dao.TransactionLogMapper;
import com.summer.dubbo.provider.entity.Order;
import com.summer.dubbo.provider.entity.TransactionLog;

import com.summer.dubbo.provider.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

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
//        orderMapper.save(order);

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
