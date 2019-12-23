package com.summer.dt.mq.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Order implements Serializable {

    private long id;
    private double price;
    private String detail;
    private Date createTime;
    private Status status;

    public enum Status {
        // 付款中
        PAYING,
        // 付款成功
        SUCCEED,
        // 有退款
        REFUNDED,
        // 订单取消
        CANCELED,
        // 订单已结束 (已支付，超过最晚退款时间) (已完全退款)
        FINISHED
    }

}
