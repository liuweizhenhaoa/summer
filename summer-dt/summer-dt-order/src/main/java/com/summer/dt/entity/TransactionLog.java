package com.summer.dt.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionLog {
    private int id;
    private long primaryKey;
    private String type;//
    private String msgBody;
    private Date createTime;
    private String status;//0-初始化 1-发送成功
    private int sendTimes;//发送次数

}
