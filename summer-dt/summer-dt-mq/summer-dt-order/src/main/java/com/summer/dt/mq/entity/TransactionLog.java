package com.summer.dt.mq.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionLog {
    private int id;
    private long primaryKey;
    private String type;//
    private String msgBody;
    private String status;//0-init 1-send success
    private int sendTimes;//send times
    private Date createTime;
    private Date updateTime;



}
