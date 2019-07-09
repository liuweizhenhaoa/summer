package com.summer.dt.xa.service.impl;

import com.summer.common.mq.MessageSender;
import com.summer.dt.mq.dao.TransactionLogMapper;
import com.summer.dt.mq.entity.TransactionLog;
import com.summer.dt.mq.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    @Autowired
    TransactionLogMapper transactionLogMapper;

    @Autowired
    MessageSender messageSender;

    @Override
    public void save(TransactionLog transactionLog) {
        transactionLogMapper.save(transactionLog);
    }

    @Override
    public void updateTransactionStatus(long primaryKey, String type, String status, Date updateTime) {
        transactionLogMapper.updateTransactionStatus(primaryKey, type, status, updateTime);
    }

    @Override
    public void processInitTransaction() {
        List<TransactionLog> transactionLogList = transactionLogMapper.findInitTransactionLogs();

        if(transactionLogList!=null && transactionLogList.isEmpty()){
            transactionLogList.stream().forEach(transactionLog ->
                messageSender.sendMsg(transactionLog)
            );
        }
    }
}
