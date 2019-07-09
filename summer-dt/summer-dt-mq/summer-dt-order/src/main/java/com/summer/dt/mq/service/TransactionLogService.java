package com.summer.dt.mq.service;

import com.summer.dt.mq.entity.TransactionLog;

import java.util.Date;

public interface TransactionLogService {

    void save(TransactionLog transactionLog);


    void updateTransactionStatus(long primaryKey, String type, String status, Date updateTime);


    void processInitTransaction();
}
