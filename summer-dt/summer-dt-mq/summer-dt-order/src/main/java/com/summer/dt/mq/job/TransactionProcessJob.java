package com.summer.dt.mq.job;

import com.summer.dt.mq.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessJob {

    @Autowired
    TransactionLogService transactionLogService;


    @Scheduled(fixedRate = 5000)
    public void transactionProcess() {
        transactionLogService.processInitTransaction();
    }


}
