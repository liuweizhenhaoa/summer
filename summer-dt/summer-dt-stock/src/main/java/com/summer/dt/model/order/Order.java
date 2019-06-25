package com.summer.dt.model.order;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private long id;
    private double price;
    private String detail;
    private Date createTime;

}
