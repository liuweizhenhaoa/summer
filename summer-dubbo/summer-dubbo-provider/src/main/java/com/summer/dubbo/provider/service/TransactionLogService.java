package com.summer.dubbo.provider.service;

import com.summer.dubbo.provider.entity.TransactionLog;

import java.util.Date;

public interface TransactionLogService {

    void save(TransactionLog transactionLog);


    void updateTransactionStatus(long primaryKey, String type, String status, Date updateTime);


    void processInitTransaction();
}
