package com.summer.dt.service;

import com.summer.dt.entity.TransactionLog;

public interface TransactionLogService {

    void save(TransactionLog transactionLog);


    void updateTransactionStatus(long primaryKey);

}
