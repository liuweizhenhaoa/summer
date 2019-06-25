package com.summer.dt.entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class Order {

    private long id;
    private Date createTime;
    private double price;
    private String detail;
}
