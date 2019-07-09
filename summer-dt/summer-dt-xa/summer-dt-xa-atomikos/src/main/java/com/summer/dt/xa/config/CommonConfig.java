package com.summer.dt.xa.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.summer.common.idgenerate.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;


/**
 * Created by codeliu on 2018/1/31.
 */
@Configuration
public class CommonConfig {

    @Value("${order.id.workerId:0}")
    private long workerId;

    @Value("${order.id.datacenterId:0}")
    private long datacenterId;

    @Bean(name = "snowflakeIdWorkerBikePath")
    public SnowflakeIdWorker snowflakeIdWorkerBikePath() {
        return new SnowflakeIdWorker(workerId,datacenterId);
    }

//    @Bean(name = "xatx")
//    @Primary
//    public JtaTransactionManager regTransactionManager () {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        UserTransaction userTransaction = new UserTransactionImp();
//        return new JtaTransactionManager(userTransaction, userTransactionManager);
//    }

}
