package com.summer.dt.service.Impl;

import com.summer.common.IdGenerate.SnowflakeIdWorker;
import com.summer.common.utils.JsonUtils;
import com.summer.dt.common.constant.OrderConstant;
import com.summer.dt.dao.OrderMapper;
import com.summer.dt.dao.TransactionLogMapper;
import com.summer.dt.entity.Order;
import com.summer.dt.entity.TransactionLog;
import com.summer.dt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    TransactionLogMapper transactionLogMapper;

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;


    @Transactional
    @Override
    public Order saveOrder(Order order) {
        order.setId(snowflakeIdWorker.nextId());
        orderMapper.save(order);

        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setCreateTime(new Date());
        transactionLog.setMsgBody(JsonUtils.toJson(order));
        transactionLog.setPrimaryKey(order.getId());
        transactionLog.setType(OrderConstant.TRANSACTION_TYPE_ORDER); //1:订单
        transactionLog.setStatus(OrderConstant.TRANSACTION_INIT);//初始化

        transactionLogMapper.save(transactionLog);

        return order;
    }
}
