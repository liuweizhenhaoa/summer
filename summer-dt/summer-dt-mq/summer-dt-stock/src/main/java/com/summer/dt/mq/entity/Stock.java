package com.summer.dt.mq.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Stock {

    private int id;
    private long goodId;
    private int num;
    private Date createTime;
    private Date updateTime;
    private int version;
}
