package com.summer.dt.job;

import com.summer.dt.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessJob {

    @Autowired
    TransactionLogService transactionLogService;


    @Scheduled(fixedRate=5000)
    public void transactionProcess(){
        transactionLogService.processInitTransaction();
    }


}
