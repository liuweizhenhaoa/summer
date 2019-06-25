package com.summer.dt.service.Impl;

import com.summer.dt.dao.TransactionLogMapper;
import com.summer.dt.entity.TransactionLog;
import com.summer.dt.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    @Autowired
    TransactionLogMapper transactionLogMapper;

    @Override
    public void save(TransactionLog transactionLog) {
        transactionLogMapper.save(transactionLog);
    }

    @Override
    public void updateTransactionStatus(long primaryKey, String type, String status, Date updateTime) {
        transactionLogMapper.updateTransactionStatus(primaryKey, type, status, updateTime);
    }
}
