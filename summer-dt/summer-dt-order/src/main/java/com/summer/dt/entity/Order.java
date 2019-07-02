package com.summer.dt.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private long id;
    private double price;
    private String detail;
    private Date createTime;

}
