package com.summer.dt.mq.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.summer.common.mq.MessageSender;
import com.summer.dt.mq.dao.OrderMapper;
import com.summer.dt.mq.dao.TransactionLogMapper;
import com.summer.dt.mq.entity.Order;
import com.summer.dt.mq.entity.TransactionLog;
import com.summer.dt.mq.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionLogServiceImpl extends ServiceImpl<TransactionLogMapper, TransactionLog> implements TransactionLogService {

//    @Autowired
//    TransactionLogMapper transactionLogMapper;

    @Autowired
    MessageSender messageSender;

    @Override
    public void save(TransactionLog transactionLog) {
        baseMapper.save(transactionLog);
    }

    @Override
    public void updateTransactionStatus(long primaryKey, String type, String status, Date updateTime) {
        baseMapper.updateTransactionStatus(primaryKey, type, status, updateTime);
    }

    @Override
    public void processInitTransaction() {
        List<TransactionLog> transactionLogList = baseMapper.findInitTransactionLogs();

        if (transactionLogList != null && transactionLogList.isEmpty()) {
            transactionLogList.stream().forEach(transactionLog ->
                    messageSender.sendMsg(transactionLog)
            );
        }
    }
}
