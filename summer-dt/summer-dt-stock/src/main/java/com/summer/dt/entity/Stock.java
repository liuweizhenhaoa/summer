package com.summer.dt.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Stock {

    private int id;
    private Date createTime;
    private double price;
    private String detail;
}
