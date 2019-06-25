package com.summer.dt.service;

import com.summer.dt.entity.TransactionLog;

import java.util.Date;

public interface TransactionLogService {

    void save(TransactionLog transactionLog);


    void updateTransactionStatus(long primaryKey, String type, String status, Date updateTime);

}
